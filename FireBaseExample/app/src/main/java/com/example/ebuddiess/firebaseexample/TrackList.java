package com.example.ebuddiess.firebaseexample;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TrackList extends ArrayAdapter {
    private Activity context;
    List<Tracks> trackList;
    LayoutInflater layoutInflater;
    public TrackList(@NonNull Activity context, List<Tracks> trackList) {
        super(context,R.layout.tracklayout,trackList);
        this.context = context;
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.tracklayout,null,true);
        TextView name = (TextView) convertView.findViewById(R.id.trackname);
        TextView rating = (TextView) convertView.findViewById(R.id.trackrating);
        name.setText(trackList.get(position).getTrackname());
        rating.setText(String.valueOf(trackList.get(position).getTrackRating()));
        return  convertView;

    }
}
