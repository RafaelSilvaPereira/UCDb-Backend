package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class SubjectUserID implements Serializable {

    @Column(name = "discipline_id")
    private Long disciplineID;

    @Column(name = "user_email")
    private String userID;

    public SubjectUserID(Long disciplineID, String userID) {
        this.disciplineID = disciplineID;
        this.userID = userID;
    }

    public Long getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Long disciplineID) {
        this.disciplineID = disciplineID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectUserID)) return false;
        SubjectUserID that = (SubjectUserID) o;
        return getDisciplineID().equals(that.getDisciplineID()) &&
                getUserID().equals(that.getUserID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDisciplineID(), getUserID());
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
