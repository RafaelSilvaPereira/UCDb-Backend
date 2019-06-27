package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.util.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private final CommentDAO commentDAO;

    @Autowired
    private final UserDAO userDAO;

    @Autowired
    private final SubjectDAO subjectDAO;

    private final Util util;

    public CommentService(CommentDAO commentDAO, UserDAO userDAO, SubjectDAO subjectDAO) {
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
        this.subjectDAO = subjectDAO;
        this.util = new Util();
    }

    public void commentSubject(String token, long id, JSONObject request) {
        User user = this.util.getUser(token, userDAO);
        Subject subject = this.subjectDAO.findById(id);
        String comment = (String) request.get("comment");

        if (user != null && subject != null && comment != null && !"".equals(comment.trim())) {
            final Comment toSaveComment = new Comment(subject, user, comment);
            this.commentDAO.save(toSaveComment);
        }
    }

    /* a gente apagou o clone */
    public void addSubcommentToSubject(String userToken, long commentId, long idSubject, JSONObject request) {


        Comment comment = this.commentDAO.findById(commentId);

        User superCommentUser = this.commentDAO.findById(commentId).getUser();
        Subject superCommentSubject = this.subjectDAO.findById(idSubject);

        /* a subCommentSubject deve ser a mesma já que subcomment simula a resposta a um comentario */
        String txtComment = (String) request.get("comment");
        User subCommentUser= this.util.getUser(userToken, userDAO);


        if (superCommentUser != null && superCommentSubject != null && subCommentUser != null
                && txtComment != null && !"".equals(txtComment.trim())) {





            Comment subComment = new Comment(superCommentSubject, subCommentUser, txtComment, comment);

            comment.getSubcomments().add(subComment);
            this.commentDAO.save(subComment);
            this.commentDAO.save(comment);
        }
    }

    public void deleteComment(String subjectId, String userEmail) {
        Long subjectIdLong = Long.parseLong(subjectId);
        final Comment comment = this.commentDAO.findByUserEmailAndSubjectId(subjectIdLong, userEmail);
        comment.setVisible(false);
        this.commentDAO.save(comment);
    }

}
