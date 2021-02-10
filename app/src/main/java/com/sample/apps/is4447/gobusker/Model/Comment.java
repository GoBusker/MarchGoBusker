package com.sample.apps.is4447.gobusker.Model;

public class Comment {
    private String comment;
    private String publisher;

// I used this Youtube video as a reference for displaying comments under posts
//    https://www.youtube.com/watch?v=V2lai8cJIkk&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=10&ab_channel=KODDev
    public Comment(String comment, String publisher){
        this.comment = comment;
        this.publisher = publisher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Comment(){

    }

}

