package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Comment {

    @NotNull
    private String userID;

    @NotNull
    private String text;

    @NotNull
    @OneToMany
    @JoinColumn(name = "comment_id")
    private List<Long> subComments;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long commentID;

    public Comment() {
    }

    public Comment(@NotNull String userID, @NotNull String text, @NotNull List<Long> subComments, @NotNull Long commentID) {
        this.userID = userID;
        this.text = text;
        this.subComments = subComments;
        this.commentID = commentID;
    }

}
