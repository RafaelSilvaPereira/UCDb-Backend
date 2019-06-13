package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Subject {

    @NotNull
    private String name;

    @Id
    @NotNull
    private long id;

    @NotNull
    private Set<String> likes;

    @NotNull
    private Set<String> dislikes;

    @OneToMany
    @JoinColumn(name = "comment_id")
    @NotNull
    private List<Long> comments;

    public Subject(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
