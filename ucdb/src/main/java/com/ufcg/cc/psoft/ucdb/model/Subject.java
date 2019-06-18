package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Subject {

    @Id
    private long id;

    private String name;

    //private ?? likes;
    //private ?? dislikes;
    //private ?? comments;

    public Subject() {}

    public Subject(long id, String name){
        this.id = id;
        this.name = name;
        //this.likes = new ??
        //this.dislikes = new ??
        //this.comments = new ??
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
