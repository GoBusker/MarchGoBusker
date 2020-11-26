package com.sample.apps.is4447.gobusker;

public class Busker {
    //simple busker class to be used as an input for the firebase system
    //declaring variables
    public String email, firstname, secondname;

    //constructor
    public Busker(){

    }
    //instantiate class
    public Busker( String firstname, String secondname, String email){
        this.email = email;
        this.firstname = firstname;
        this.secondname = secondname;
    }
}
