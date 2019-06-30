package com.ufcg.cc.psoft.ucdb.view;


import java.util.Objects;

public class StudentView {

    private String email;
    private String firstName;
    private String secondName;

    public StudentView(String email, String firstName, String secondName){
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentView studentView = (StudentView) o;
        return Objects.equals(email, studentView.email) &&
                Objects.equals(firstName, studentView.firstName) &&
                Objects.equals(secondName, studentView.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, secondName);
    }

    @Override
    public String toString() {
        return "StudentView{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
