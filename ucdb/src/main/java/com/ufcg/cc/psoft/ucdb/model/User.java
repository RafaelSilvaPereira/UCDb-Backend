package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    private String email;
    private String firstName;
    private String secondName;
    private String password;

    @ManyToMany
    @JoinTable(
            name="liked_course",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> enjoiyed;

    @ManyToMany
    @JoinTable(
            name="disliked_course",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> disliked;

    @OneToMany(
            mappedBy = "user",
            cascade =  CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserEvalueSubject> evaluatedSubjects;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> userComments;


    public User() {
        this.enjoiyed = new HashSet<>();
        this.disliked = new HashSet<>();
        this.evaluatedSubjects = new HashSet<>();
    }

    public User( String email,  String firstName,  String secondName,  String password) {
        this();
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;

    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserEvalueSubject> getEvaluatedSubjects() {
        return evaluatedSubjects;
    }

    public void setEvaluatedSubjects(Set<UserEvalueSubject> evaluatedSubjects) {
        this.evaluatedSubjects = evaluatedSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Set<Subject> getEnjoiyed() {
        return enjoiyed;
    }

    public void setEnjoiyed(Set<Subject> enjoiyed) {
        this.enjoiyed = enjoiyed;
    }

    public Set<Subject> getDisliked() {
        return disliked;
    }

    public void setDisliked(Set<Subject> disliked) {
        this.disliked = disliked;
    }
}
