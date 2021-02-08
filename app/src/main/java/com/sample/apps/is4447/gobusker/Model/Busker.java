package com.sample.apps.is4447.gobusker.Model;

public class Busker {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    //simple busker class to be used as an input for the firebase system
    //declaring variables
    public String email, firstname, bio, id, imageUrl, username;

    //constructor
    public Busker(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //instantiate class
    public Busker( String firstname, String email, String bio, String id, String imageUrl, String username){
        this.email = email;
        this.firstname = firstname;
        this.bio = bio;
        this.id = id;
        this.imageUrl = imageUrl;
        this.username = username;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
