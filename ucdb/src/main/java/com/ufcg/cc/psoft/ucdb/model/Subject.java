package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

//    private List<String> likes;
//
//    private List<String> dislikes;
//
//    private Map<String, Float> rate;
//
//    private List<Comment> comments;

    public Subject() {}

    public Subject(String name) {
        this.name = name;
    }

    public Subject(long id, String name){
        this.id = id;
        this.name = name;
//        this.likes = new ArrayList<>();
//        this.dislikes = new ArrayList<>();
//        this.rate = new HashMap<>();
//        this.comments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
