package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity(name = "Subject")
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "enjoiyed")
    private Set<User> userLiked;

    @ManyToMany(mappedBy = "disliked")
    private Set<User> userDisliked;

    @OneToMany(
            mappedBy = "subject",
            cascade =  CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserEvalueSubject> userEvaluation;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> subjectComments;

    public Subject() {
        this.userLiked = new HashSet<>();
        this.userDisliked = new HashSet<>();
        this.userEvaluation = new HashSet<>();
    }

    public Subject(String name) {
        this();
        this.name = name;
    }

    public Subject(long id, String name){
        this(name);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return getId() == subject.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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

    public Set<User> getUserLiked() {
        return userLiked;
    }

    public void setUserLiked(Set<User> userLiked) {
        this.userLiked = userLiked;
    }

    public Set<User> getUserDisliked() {
        return userDisliked;
    }

    public void setUserDisliked(Set<User> userDisliked) {
        this.userDisliked = userDisliked;
    }

    public Set<UserEvalueSubject> getUserEvaluation() {
        return userEvaluation;
    }

    public void setUserEvaluation(Set<UserEvalueSubject> userEvaluation) {
        this.userEvaluation = userEvaluation;
    }
}
