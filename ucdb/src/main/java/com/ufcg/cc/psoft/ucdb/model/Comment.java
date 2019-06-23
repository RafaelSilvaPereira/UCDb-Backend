package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "subject_comments")
public class Comment {

    @EmbeddedId
    private SubjectUserID id;

    @ManyToOne /*definindo o auto relacionamento*/
    private Comment superComment;

    @OneToMany(mappedBy = "superComment") /*definindo o auto relacionamento*/
    private List<Comment> subcomments;

    @Column(name = "subjectComment") /* definindo o texto*/
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subject")
    private Subject subject;

    public Comment() {
        this.superComment = new Comment();
        this.subcomments = new ArrayList<>();
    }

    public Comment(Subject subject, User user, String comment) {
        this();
        this.subject = subject;
        this.user = user;
        this.comment = comment;
    }

}
