package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_evalue_subject")
public class UserEvalueSubject {

    @EmbeddedId
    private SubjectUserID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectID")
    private Subject subject;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userEmail")
    private User user;

    @Column(name = "subjectNote")
    private Double evaluation;

    public UserEvalueSubject() {
    }

    public UserEvalueSubject(Subject subject, User user, Double evaluation) {
        this.subject = subject;
        this.user = user;
        this.evaluation = evaluation;
    }

    public SubjectUserID getId() {
        return id;
    }

    public void setId(SubjectUserID id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEvalueSubject)) return false;
        UserEvalueSubject that = (UserEvalueSubject) o;
        return getId().equals(that.getId()) &&
                getSubject().equals(that.getSubject()) &&
                getUser().equals(that.getUser()) &&
                getEvaluation().equals(that.getEvaluation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getUser(), getEvaluation());
    }
}
