package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface CommentDAO <T,ID extends Serializable> extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);

    void delete(Comment comment);

//    @Query(value = "SELECT c FROM Comment c WHERE c.id=:id")
//    Comment findByIdAlternative(@Param("id") Long id);

//    Comment findByCommentIdAndStudentEmail(@Param("id") Long commentId, @Param("email") String studentEmail);

    @Query(value = "SELECT c FROM Comment c WHERE c.id=:id")
    Comment findByIdA(@Param("id")long commentId);

    Comment findById(long id);

}
