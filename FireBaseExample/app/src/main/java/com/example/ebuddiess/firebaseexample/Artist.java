package com.example.ebuddiess.firebaseexample;

public class Artist {
    String artistname;
    String genre;
    String artistid;

    public Artist() {

    }

    public Artist(String artistname, String genre, String artistid) {
        this.artistname = artistname;
        this.genre = genre;
        this.artistid = artistid;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtistid() {
        return artistid;
    }

    public void setArtistid(String artistid) {
        this.artistid = artistid;
    }
}
