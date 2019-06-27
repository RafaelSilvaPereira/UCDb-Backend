package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.SubjectUserID;
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

    public void commentSubject(JSONObject request) {
        User user = this.util.getUser(request, userDAO);
        Subject subject = this.util.getSubject(request, subjectDAO);
        String comment = (String) request.get("comment");

        if (user != null && subject != null && comment != null && !"".equals(comment.trim())) {
            final Comment toSaveComment = new Comment(subject, user, comment);
            this.commentDAO.save(toSaveComment);
        }
    }

    public void addSubcommentToSubject(JSONObject request) {
        User superCommentUser = this.userDAO.userFindByEmail((String) request.get("user_token")).superficialCopy();
        Subject superCommentSubject = this.util.getSubject(request, subjectDAO).superficialClone();
        User subCommentUser = this.util.getSuperUserComment(request, "comment_owner", userDAO)
                .superficialCopy();

        /* a subCommentSubject deve ser a mesma já que subcomment simula a resposta a um comentario */
        String txtComment = (String) request.get("comment");

        if (superCommentUser != null && superCommentSubject != null && subCommentUser != null
                && txtComment != null && !"".equals(txtComment.trim())) {
            SubjectUserID subjectUserID = new SubjectUserID(superCommentSubject.getId(), superCommentUser.getEmail());

            Comment superComment = this.commentDAO.findByIdAlternative(subjectUserID);

            Comment subComment = new Comment(superCommentSubject, subCommentUser, txtComment, superComment);

            superComment.getSubcomments().add(subComment);
            this.commentDAO.save(subComment);
            this.commentDAO.save(superComment);
        }
    }

    public void deleteComment(String subjectId, String userEmail) {
        Long subjectIdLong = Long.parseLong(subjectId);
        final Comment comment = this.commentDAO.findByIdAlternative(new SubjectUserID(subjectIdLong, userEmail));
        comment.setVisible(false);
        this.commentDAO.save(comment);
    }

}
