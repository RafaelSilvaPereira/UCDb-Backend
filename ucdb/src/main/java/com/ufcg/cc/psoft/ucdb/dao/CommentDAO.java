package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.SubjectUserID;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface CommentDAO <T,ID extends Serializable> extends JpaRepository<Comment, SubjectUserID> {
    Comment save(Comment comment);

    void delete(Comment comment);
}
