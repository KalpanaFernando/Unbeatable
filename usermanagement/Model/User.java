package com.example.usermanagement.Model;

import java.util.Date;

public class User {

    private String imgUri, fname, usrname, bio;
    private Date date;
    private String views, img;


    public User(String imgUri, String fname, String usrname, String bio, Date date, String  views, String  img) {
        this.imgUri = imgUri;
        this.fname = fname;
        this.usrname = usrname;
        this.bio = bio;
        this.date = date;
        this.views = views;
        this.img = img;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
