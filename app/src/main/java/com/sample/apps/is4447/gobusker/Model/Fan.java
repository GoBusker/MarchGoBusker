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



    //simple busker class to be used as an input for the firebase system
    //declaring variables
    public String email;
    public String firstname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String username;
    public String id;

    //constructor
    public Fan(){

    }
    //instantiate class
    public Fan( String firstname, String username, String email){
        this.firstname = firstname;
        this.username = username;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
