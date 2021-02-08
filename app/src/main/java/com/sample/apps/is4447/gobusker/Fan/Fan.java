package com.sample.apps.is4447.gobusker.Fan;

public class Fan {
    //simple busker class to be used as an input for the firebase system
    //declaring variables
    public String email, firstname, secondname;

    //constructor
    public Fan(){

    }
    //instantiate our variables
    public Fan( String firstname, String secondname, String email){
        this.email = email;
        this.firstname = firstname;
        this.secondname = secondname;
    }
}
