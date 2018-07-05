package com.example.ebuddiess.authdemo.User;

public class UserData {
    private String emailid;
    private String firstName;
    private String lastName;
    private String uid;
    private String profilepicurl;

    public UserData() {

    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfilepicurl() {
        return profilepicurl;
    }

    public void setProfilepicurl(String profilepicurl) {
        this.profilepicurl = profilepicurl;
    }

    public UserData(String emailid, String uid) {
        this.emailid = emailid;
        this.uid = uid;
        firstName = "";
        lastName = "";
        profilepicurl = "";

    }

    public UserData(String emailid,String firstName, String lastName, String uid, String profilepicurl) {
        this.emailid = emailid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uid = uid;
        this.profilepicurl = profilepicurl;
    }
}
