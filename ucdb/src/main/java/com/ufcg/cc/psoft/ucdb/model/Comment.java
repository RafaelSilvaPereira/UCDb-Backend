package com.ufcg.cc.psoft.ucdb.model;

import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "subjectComment") /* definindo o texto*/
    private String comment;

    @Column(name = "postDate")
    private String date;

    @Column(name = "visible")
    private Boolean visible;

    @ManyToOne /*definindo o auto relacionamento*/
    private Comment superComment;

    @OneToMany(mappedBy = "superComment") /*definindo o auto relacionamento*/
    private List<Comment> subcomments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    public Comment() {
        this.comment = "";
        this.superComment = null;
        this.subcomments = new ArrayList<>();
        this.visible = true;
        final Date date = new Date();
        String commentDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String commentHour = new SimpleDateFormat("HH:mm:ss").format(date);
        this.date = String.format("at: %s on day: %s", commentHour, commentDate);

    }

    public Comment(Subject subject, Student student, String comment) {
        this();
        this.subject = subject;
        this.student = student;
        this.comment = comment;
    }

    public Comment(Subject subject, Student student, String comment, Comment superComment) {
        this(subject, student, comment);
        this.superComment = superComment;
    }


    public Comment getSuperComment() {
        return superComment;
    }

    public void setSuperComment(Comment superComment) {
        this.superComment = superComment;
    }

    public List<Comment> getSubcomments() {
        return subcomments;
    }

    public void setSubcomments(List<Comment> subcomments) {
        this.subcomments = subcomments;
    }

    public String getComment() {
        return comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Boolean isVisble() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getId() == comment.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        String toString;
        if (!this.isNIL()) {
            toString = String.format("Comement: {\n id = {%s, %s}\n superComment = {%s} subComments = {%s}," +
                            " textComment = {%s}",
                    this.getId(),  this.getSuperComment().toString(),
                    Arrays.toString(this.getSubcomments().toArray()));
        } else {
            toString = "NIL";
        }

        return toString;
    }


    public boolean isNIL() {
        return this.getId() == 0;
    }

    public Comment superficialCopy() {
        Comment commentClone = null;
        if (this.visible && this.getSuperComment() == null) {

            Subject subjectClone = getSubjectClone();
            Student studentCopy = getStudentClone();
            commentClone = new Comment(subjectClone, studentCopy, this.getComment());
            getSuperCommentClone(commentClone);
            commentClone.setSubcomments(this.subcommentsClone());

        }

        return commentClone;
    }

    private void getSuperCommentClone(Comment commentClone) {
        if (this.getSuperComment() != null && !this.getSuperComment().isNIL()) {
            Comment superCommentClone = new Comment(this.superComment.getSubject(), this.superComment.getStudent(),
                    this.superComment.getComment());
            commentClone.setSuperComment(superCommentClone);
        } else
            commentClone.setSuperComment(null);
    }

    @Nullable
    private Subject getSubjectClone() {
        Subject subjectClone;
        if (this.subject != null && !this.subject.isNIL()) {
            subjectClone = new Subject(this.getSubject().getId(), this.getSubject().getName());
        } else {
            subjectClone = null;
        }
        return subjectClone;
    }

    @Nullable
    private Student getStudentClone() {
        Student studentCopy;
        if (this.student != null && !this.student.isNIL()) {
            studentCopy = new Student(this.getStudent().getEmail(), this.getStudent().getFirstName(),
                    this.getStudent().getSecondName(), this.getStudent().getPassword());
        } else {
            studentCopy = null;
        }
        return studentCopy;
    }

    private List<Comment> subcommentsClone() {
        List<Comment> subcomments = new ArrayList<>();
        for (Comment sc : this.getSubcomments()) {
            Subject subjectClone = new Subject(sc.getSubject().getId(), sc.getSubject().getName());
            Student studentClone = sc.getStudent().superficialCopy();
            Comment subcommentClone = new Comment(subjectClone, studentClone, sc.getComment());
            subcomments.add(subcommentClone);
        }
        return subcomments;
    }

    public boolean isSubComment() {
        return this.getSuperComment() != null;
    }
}
