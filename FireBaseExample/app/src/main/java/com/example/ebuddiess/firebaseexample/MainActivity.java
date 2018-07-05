package com.example.ebuddiess.firebaseexample;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button submit;
Artist artist;
EditText artist_edt;
ListView artistListView;
List<Artist> list;
Spinner spinner;
DatabaseReference dbArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button)findViewById(R.id.submit);
        artist_edt = (EditText)findViewById(R.id.txt_artist);
        spinner = (Spinner)findViewById(R.id.spinner);
        list = new ArrayList<Artist>();
        artistListView = (ListView)findViewById(R.id.artistlist);
        dbArtist = FirebaseDatabase.getInstance().getReference("Artist");
        submit.setOnClickListener(this);
        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Artist artist = (Artist) adapterView.getItemAtPosition(i);
                 Intent i1 =   new Intent(MainActivity.this,AddTracks.class);
                i1.putExtra("id", artist.getArtistid());
                i1.putExtra("name", artist.getArtistname());
                startActivity(i1);
            }
        });

        artistListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                artist = list.get(i);
                showupdateidlaog(artist.getArtistid(),artist.getArtistname());
                return  false;
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        dbArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Artist artist = ds.getValue(Artist.class);
                    list.add(artist);
                }
                ArtistList artistList = new ArtistList(MainActivity.this, list);
                artistListView.setAdapter(artistList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        addArtist();
    }

    private void showupdateidlaog(final String artistid, String artistname){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.updatedialog,null);
        builder.setView(dialogView);

        final EditText newname = (EditText)dialogView.findViewById(R.id.newname);
        TextView currentname = (TextView)dialogView.findViewById(R.id.currentname);
        Button update = (Button)dialogView.findViewById(R.id.update);
        builder.setTitle("UPDATE ARTIST NAME"+artistname);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=artistid;
                String newnametxt =  newname.getText().toString();
                updateArtist(id,newnametxt);
                alertDialog.dismiss();
            }
        });
    }

    public void updateArtist(String id,String name){
        Artist artist = new Artist();
        artist.setArtistid(id);
        artist.setArtistname(name);
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Artist").child(id);
        dr.setValue(artist);
        Toast.makeText(MainActivity.this,"ARTIST UPDATED",Toast.LENGTH_LONG).show();
    }
    public void addArtist(){
        String name = artist_edt.getText().toString();
        String genre = spinner.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name)){
            String id = dbArtist.push().getKey();
            Artist artist = new Artist(name,genre,id);
            dbArtist.child(id).setValue(artist);
            Toast.makeText(this,"Artist Added Sucessfully",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Enter Name",Toast.LENGTH_LONG).show();
        }
    }
}
