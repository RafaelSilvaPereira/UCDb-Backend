package com.ufcg.cc.psoft.ucdb.model;

public class Comment {

    private String comment;
    private String userID;

    public Comment(String comment, String userID) {
        this.comment = comment;
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public String getUserID() {
        return userID;
        
    }
}
