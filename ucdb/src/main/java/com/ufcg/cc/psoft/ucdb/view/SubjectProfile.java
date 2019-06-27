package com.ufcg.cc.psoft.ucdb.view;

import java.util.Collection;

public class SubjectProfile extends GenericSubjectProfile {

    private int likes;
    private int dislikes;
    //    private Double subjectAverage;
    private Collection<CommentView> comments;


    public SubjectProfile() {
        super();
    }

    public SubjectProfile(Long id, String name, int dislikes, int likes, Collection<CommentView> comments) {
        super(id, name);
        this.dislikes = dislikes;
        this.likes = likes;
        this.comments = comments;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public Collection<CommentView> getComments() {
        return comments;
    }

//    public Double getSubjectAverage() {
//        return subjectAverage;
//    }

    public int getProportion() {
        int answer = 0;
        if (this.getLikes() + this.getDislikes() == 0) {
            answer = 0;
        } else {
            answer = this.getLikes() / (this.getLikes() + this.getDislikes());
        }
        return answer;
    }

//    private Double calculeSubjectAvarege(Set<UserEvalueSubject> usersEvaluation) {
//        double sum = 0;
//        int size = 0;
//        for (UserEvalueSubject userEvalueSubject : usersEvaluation) {
//            sum += userEvalueSubject.getEvaluation();
//            size++;
//        }
//
//        Double avg = (size == 0) ? 0 : (sum / size);
//
//        return Double.parseDouble(String.format("%.2f", avg).replace(",", "."));
//    }

}
