package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    private String email;
    private String firstName;
    private String secondName;
    private String password;

    public User() {
    }

    public User(String email, String firstName, String secondName, String password) {
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

}
