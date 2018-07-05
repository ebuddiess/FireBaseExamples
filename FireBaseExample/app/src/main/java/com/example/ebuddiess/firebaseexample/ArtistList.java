package com.example.ebuddiess.firebaseexample;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ArtistList extends ArrayAdapter {
    private Activity context;
    List<Artist> artistList;
    LayoutInflater layoutInflater;
    public ArtistList(@NonNull Activity context,List<Artist> artistList) {
        super(context,R.layout.listlayout,artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         layoutInflater = context.getLayoutInflater();
         convertView = layoutInflater.inflate(R.layout.listlayout,null,true);
         TextView name = (TextView) convertView.findViewById(R.id.txtviewname);
         TextView genre = (TextView) convertView.findViewById(R.id.txtviewgenre);
        name.setText(artistList.get(position).getArtistname());
        genre.setText(artistList.get(position).getGenre());
        return  convertView;

    }
}
