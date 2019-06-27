package com.ufcg.cc.psoft.ucdb.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

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

    //
//    @OneToMany(
//            mappedBy = "subject",
//            cascade =  CascadeType.ALL,
//            orphanRemoval = true
//    )
////    private Set<UserEvalueSubject> userEvaluation;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> subjectComments;

    public Subject() {
        this.userLiked = new HashSet<>();
        this.userDisliked = new HashSet<>();
//        this.userEvaluation = new HashSet<>();
    }

    public Subject(String name) {
        this();
        this.name = name;
    }

    public Subject(long id, String name){
        this(name);
        this.id = id;
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

//    public Set<UserEvalueSubject> getUserEvaluation() {
//        return userEvaluation;
//    }

//    public void setUserEvaluation(Set<UserEvalueSubject> userEvaluation) {
//        this.userEvaluation = userEvaluation;
//    }

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

    public Subject superficialClone() {
        Subject subjectClone = new Subject();
        if (!this.isNIL()) {
            Set<User> likes = cloneUserOption(this.getUserLiked());
            Set<User> dislikes = cloneUserOption(this.getUserDisliked());
//            Set<UserEvalueSubject> evaluationClone = cloneUserEvaluation();
            List<Comment> commentsClone = cloneUserComments();

            subjectClone.setId(this.getId());
            subjectClone.setName(this.getName());
            subjectClone.setUserLiked(likes);
            subjectClone.setUserDisliked(dislikes);
//            subjectClone.setUserEvaluation(evaluationClone);
            subjectClone.setSubjectComments(commentsClone);


        }
        return subjectClone;
    }

    private List<Comment> cloneUserComments() {
        List<Comment> comments = new ArrayList<>();
        if (!isANILCOllection(this.getSubjectComments())) {
            for (Comment comment : this.getSubjectComments()) {
                if (comment != null && !comment.isNIL() && !comment.isSubComment() && comment.isVisble()) {
                    comments.add(comment.superficialCopy());
                }
            }
        }
        return comments;
    }

    private boolean isANILCOllection(Collection collection) {
        return collection.isEmpty() || collection == null;
    }

    @NotNull
    private Set<User> cloneUserOption(Set<User> local) {
        Set<User> userOption = new HashSet<>();
        if (!isANILCOllection(local)) {
            for (User user : local) {
                if (user != null && !user.isNIL()) {
                    userOption.add(user.superficialCopy());
                }
            }
        }
        return userOption;
    }


//    @NotNull
//    private Set<UserEvalueSubject> cloneUserEvaluation() {
//        Set<UserEvalueSubject> evalueSubjects = new HashSet<>();
//
//        if (!isANILCOllection(this.getUserEvaluation())) {
//            for (UserEvalueSubject userEvalueSubject : this.getUserEvaluation()) {
//                if (userEvalueSubject != null && !userEvalueSubject.isNIL()) {
//                    evalueSubjects.add(userEvalueSubject.superficialCopy());
//                }
//            }
//        }
//        return evalueSubjects;
//    }

    @Override
    public String toString() {
        String toString;
        if (!this.isNIL()) {
            toString = String.format("{\nid = {%s}, \nname = {%s}, \nlikes = {%s}, \ndislikes = {%s}, \ncomments = {%s}, " +
                            "\nevaluation = {%s}}", this.getId(), this.getName(), this.getUserLiked().size(),
                    this.getUserDisliked().size(), this.getSubjectComments().size());
        } else {
            toString = "NIL";
        }

        return toString;
    }

    public boolean isNIL() {
        return this.getId() == 0;
    }

//    public double getAverage() {
//        int sum = 0;
//        int cont = 1;
//        for(UserEvalueSubject userEvalueSubject : this.getUserEvaluation()) {
//            sum += userEvalueSubject.getEvaluation();
//            cont++;
//        }
//
//        return sum / cont;
//    }
}
