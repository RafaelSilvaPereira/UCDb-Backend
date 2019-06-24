package com.ufcg.cc.psoft.ucdb.view;

import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.UserEvalueSubject;

import java.util.Set;

public class SubjectProfile {

    private Long id;
    private int dislikes;
    private int likes;
    private String name;

    private Set<Comment> comments;
    private Double subjectAverage;

    public SubjectProfile() {
    }

    public SubjectProfile(Subject subject) {
        if (subject != null) {
            this.id = subject.getId();
            this.name = subject.getName();
            this.comments = subject.getSubjectComments();
            this.likes = subject.getUserLiked().size();
            this.dislikes = subject.getUserDisliked().size();
            this.subjectAverage = calculeSubjectAvarege(subject.getUserEvaluation());
        }
    }

    public Long getId() {
        return id;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public String getName() {
        return name;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Double getSubjectAverage() {
        return subjectAverage;
    }

    private Double calculeSubjectAvarege(Set<UserEvalueSubject> usersEvaluation) {
        double sum = 0;
        int size = 0;
        for (UserEvalueSubject userEvalueSubject : usersEvaluation) {
            sum += userEvalueSubject.getEvaluation();
            size++;
        }

        Double avg = (size == 0) ? 0 : (sum / size);

        return Double.parseDouble(String.format("%.2f", avg).replace(",", "."));
    }
}