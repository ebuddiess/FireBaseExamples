package com.example.ebuddiess.firebaseexample;

public class Tracks {
String trackid;
String trackname;
int trackRating;
public Tracks(){

}

    public Tracks(String trackid, String trackname, int trackRating) {
        this.trackid = trackid;
        this.trackname = trackname;
        this.trackRating = trackRating;
    }

    public String getTrackid() {
        return trackid;
    }

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }

    public String getTrackname() {
        return trackname;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    public int getTrackRating() {
        return trackRating;
    }

    public void setTrackRating(int trackRating) {
        this.trackRating = trackRating;
    }
}
