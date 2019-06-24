package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_evalue_subject")
public class UserEvalueSubject {

    @EmbeddedId
    private SubjectUserID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subject_id")
    private Subject subject;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_email")
    private User user;

    @Column(name = "subject_note")
    private Double evaluation;



    public UserEvalueSubject(Subject subject, User user, Double evaluation) {
        this.id = new SubjectUserID(subject.getId(), user.getEmail());
        this.subject = subject;
        this.user = user;
        this.evaluation = evaluation;
    }

    public UserEvalueSubject() {
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
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UserEvalueSubject superficialCopy() {

        Subject subjectClone;
        if (this.subject != null && !this.subject.isNIL()) {
            subjectClone = new Subject(this.getSubject().getId(), this.getSubject().getName());
        } else {
            subjectClone = null;
        }
        User userCopy;
        if (this.user != null && !this.user.isNIL()) {
            userCopy = new User(this.getUser().getEmail(), this.getUser().getFirstName(), this.getUser().getSecondName(), this.getUser().getPassword());
        } else {
            userCopy = null;
        }
        UserEvalueSubject userEvalueSubject = new UserEvalueSubject(subjectClone, userCopy, this.getEvaluation());
        return userEvalueSubject;
    }

    public boolean isNIL() {
        return this.getId().isNIl() && this.getSubject().isNIL() && this.getUser().isNIL() && (this.getEvaluation() == null || this.getEvaluation() == 0);
    }
}
