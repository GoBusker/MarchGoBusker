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
    public String email, firstname, bio, id, imageUrl, username, payment10, payment2, payment5, payment20;

    //constructor
    public Busker(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPayment10() {
        return payment10;
    }

    public void setPayment10(String payment10) {
        this.payment10 = payment10;
    }

    public String getPayment2() {
        return payment2;
    }

    public void setPayment2(String payment2) {
        this.payment2 = payment2;
    }

    public String getPayment5() {
        return payment5;
    }

    public void setPayment5(String payment5) {
        this.payment5 = payment5;
    }

    public String getPayment20() {
        return payment20;
    }

    public void setPayment20(String payment20) {
        this.payment20 = payment20;
    }

    //instantiate class
    public Busker( String firstname, String email, String bio, String id, String imageUrl, String username, String payment10, String payment2, String payment5, String payment20){
        this.email = email;
        this.firstname = firstname;
        this.bio = bio;
        this.id = id;
        this.imageUrl = imageUrl;
        this.username = username;
        this.payment10 = payment10;
        this.payment2 = payment2;
        this.payment5 = payment5;
        this.payment20 = payment20;
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
