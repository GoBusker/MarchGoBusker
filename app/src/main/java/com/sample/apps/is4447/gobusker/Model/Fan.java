package com.sample.apps.is4447.gobusker.Model;

public class Fan {
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
    public String email, firstname, bio, id;

    //constructor
    public Fan(){

    }
    //instantiate class
    public Fan( String firstname, String email, String bio){
        this.email = email;
        this.firstname = firstname;
        this.bio = bio;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
