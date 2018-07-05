package com.example.ebuddiess.firebaseexample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTracks extends AppCompatActivity implements View.OnClickListener {
    TextView artistName;
    EditText trackname;
    SeekBar seekBar;
    Button submit;
    List<Tracks> tracklist;
    ListView listView;
    DatabaseReference databaseReferencetracks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tracks);
        trackname = (EditText)findViewById(R.id.track_txt);
        artistName = (TextView)findViewById(R.id.artistname);
        seekBar= (SeekBar) findViewById(R.id.seekbarRating);
        tracklist = new ArrayList<Tracks>();
        submit = (Button)findViewById(R.id.submitrack);
        listView = (ListView)findViewById(R.id.Tracklist);
        submit.setOnClickListener(this);
        String id  =getIntent().getStringExtra("id");
        String name  =getIntent().getStringExtra("name");

        databaseReferencetracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
        artistName.setText(name);
     }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferencetracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracklist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Tracks track = snapshot.getValue(Tracks.class);
                    tracklist.add(track);
                }
                TrackList trackListadapter =  new TrackList(AddTracks.this,tracklist);
                listView.setAdapter(trackListadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
    saveTrack();
    }

    private void saveTrack() {
    String tracknametxt =trackname.getText().toString();
    int rating = seekBar.getProgress();
        if(!TextUtils.isEmpty(tracknametxt)){
            String id = databaseReferencetracks.push().getKey();
            Tracks tracks = new Tracks(id,tracknametxt,rating);
            databaseReferencetracks.child(id).setValue(tracks);
            Toast.makeText(this,"Track Added Sucessfully",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Enter Track Name",Toast.LENGTH_LONG).show();
        }
    }
}
