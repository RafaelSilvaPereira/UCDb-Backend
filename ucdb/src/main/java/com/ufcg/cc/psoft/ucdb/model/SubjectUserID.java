//package com.ufcg.cc.psoft.ucdb.model;
//
//import org.springframework.data.annotation.Id;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Embeddable
//public class SubjectUserID implements Serializable {
//
//    @Column(name = "subject_id")
//    private Long subjectID;
//
//    @Column(name = "user_email")
//    private String userID;
//
//    @Column(name = "comment")
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private  long idcomment;
//
//
//
//
//
//    public SubjectUserID() {
//    }
//
//    public SubjectUserID(Long subjectID, String userID) {
//        this.subjectID = subjectID;
//        this.userID = userID;
//    }
//
//    public Long getSubjectID() {
//        return subjectID;
//    }
//
//    public void setSubjectID(Long subjectID) {
//        this.subjectID = subjectID;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof SubjectUserID)) return false;
//        SubjectUserID that = (SubjectUserID) o;
//        return getSubjectID().equals(that.getSubjectID()) &&
//                getUserID().equals(that.getUserID());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getSubjectID(), getUserID());
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    @Override
//    public String toString() {
//        String toString;
//        if (!this.isNIl()) {
//            toString = String.format("SubjectUserID {subjectID = {%s}, userID = {%s}}",
//                    this.getSubjectID(), this.getUserID());
//        } else {
//            toString = "NIL";
//        }
//        return toString;
//    }
//
//    public boolean isNIl() {
//        return this.getSubjectID() == null && this.getUserID() == null;
//    }
//}
