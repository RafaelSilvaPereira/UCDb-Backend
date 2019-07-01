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
    private Set<Student> studentLiked;

    @ManyToMany(mappedBy = "disliked")
    private Set<Student> studentDisliked;

    @OneToMany(
            mappedBy = "subject",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> subjectComments;

    public Subject() {
        this.studentLiked = new HashSet<>();
        this.studentDisliked = new HashSet<>();
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

    public Set<Student> getStudentLiked() {
        return studentLiked;
    }

    public void setStudentLiked(Set<Student> studentLiked) {
        this.studentLiked = studentLiked;
    }

    public Set<Student> getStudentDisliked() {
        return studentDisliked;
    }

    public void setStudentDisliked(Set<Student> studentDisliked) {
        this.studentDisliked = studentDisliked;
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

    public Subject superficialClone() {
        Subject subjectClone = new Subject();
        if (!this.isNIL()) {
            Set<Student> likes = cloneStudentOption(this.getStudentLiked());
            Set<Student> dislikes = cloneStudentOption(this.getStudentDisliked());
            List<Comment> commentsClone = cloneStudentComments();
            subjectClone.setId(this.getId());
            subjectClone.setName(this.getName());
            subjectClone.setStudentLiked(likes);
            subjectClone.setStudentDisliked(dislikes);
            subjectClone.setSubjectComments(commentsClone);


        }
        return subjectClone;
    }

    private List<Comment> cloneStudentComments() {
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
        return Objects.requireNonNull(collection).isEmpty();
    }

    @NotNull
    private Set<Student> cloneStudentOption(Set<Student> local) {
        Set<Student> studentOption = new HashSet<>();
        if (!isANILCOllection(local)) {
            for (Student student : local) {
                if (student != null && !student.isNIL()) {
                    studentOption.add(student.superficialCopy());
                }
            }
        }
        return studentOption;
    }


    @Override
    public String toString() {
        String toString;
        if (!this.isNIL()) {
            toString = String.format("{\nid = {%s}, \nname = {%s}, \nlikes = {%s}, \ndislikes = {%s}, \ncomments = {%s}, " +
                            "\nevaluation = {%s}}", this.getId(), this.getName(), this.getStudentLiked().size(),
                    this.getStudentDisliked().size(), this.getSubjectComments().size());
        } else {
            toString = "NIL";
        }

        return toString;
    }

    public boolean isNIL() {
        return this.getId() == 0;
    }
}
