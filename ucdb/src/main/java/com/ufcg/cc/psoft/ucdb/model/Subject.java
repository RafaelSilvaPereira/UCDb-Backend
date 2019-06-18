package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Subject {

    @Id
    private String id;

    private String name;

    //private ?? likes;
    //private ?? dislikes;
    //private ?? comments;

    public Subject() {}

    public Subject(String id, String name){
        this.id = id;
        this.name = name;
        //this.likes = new ??
        //this.dislikes = new ??
        //this.comments = new ??
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
