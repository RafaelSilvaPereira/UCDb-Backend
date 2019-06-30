package com.ufcg.cc.psoft.ucdb.view;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CommentView {

    private String studentName;
    private String studentSecondName;
    private String comment;
    private Collection<CommentView> subcomments; /*CommentView vai gerar uma lista de comentarios que são comments view*/
    private long commentID;
    private String commentDate;
    private String commentHour;

    public CommentView(String studentName, String studentSecondName, String comment, Collection<CommentView> subcomments
            , long commentID, String commentDate, String commentHour) {
        this.studentName = studentName;
        this.studentSecondName = studentSecondName;
        this.comment = comment;
        this.subcomments = subcomments;
        this.commentID = commentID;
        this.commentDate = commentDate;
        this.commentHour = commentHour;
    }

    public void setSubcomments(Collection<CommentView> subcomments) {
        this.subcomments = subcomments;
    }

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSecondName() {
        return studentSecondName;
    }

    public void setStudentSecondName(String studentSecondName) {
        this.studentSecondName = studentSecondName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<CommentView> getSubcomments() {
        return subcomments;
    }

    public void setSubcomments(List<CommentView> subcomments) {
        this.subcomments = subcomments;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }


    public String getCommentHour() {
        return commentHour;
    }

    public void setCommentHour(String commentHour) {
        this.commentHour = commentHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentView that = (CommentView) o;
        return Objects.equals(studentName, that.studentName) &&
                Objects.equals(studentSecondName, that.studentSecondName) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(subcomments, that.subcomments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, studentSecondName, comment, subcomments);
    }

    @Override
    public String toString() {
        return "CommentView{" +
                "studentName='" + studentName + '\'' +
                ", studentSecondName='" + studentSecondName + '\'' +
                ", comment='" + comment +
                '}';
    }
}
