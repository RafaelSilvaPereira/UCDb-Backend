package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.StudentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.view.CommentView;
import com.ufcg.cc.psoft.util.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private final CommentDAO commentDAO;

    @Autowired
    private final StudentDAO studentDAO;

    @Autowired
    private final SubjectDAO subjectDAO;

    private final Util util;

    public CommentService(CommentDAO commentDAO, StudentDAO studentDAO, SubjectDAO subjectDAO) {
        this.commentDAO = commentDAO;
        this.studentDAO = studentDAO;
        this.subjectDAO = subjectDAO;
        this.util = new Util();
    }

    public CommentView commentSubject(String token, long id, JSONObject request) {
        CommentView response = null;
        Student student = this.util.getStudent(token, studentDAO);
        Subject subject = this.subjectDAO.findById(id);
        String comment = (String) request.get("comment");

        if (student != null && subject != null && comment != null && !"".equals(comment.trim())) {
            final Comment toSaveComment = new Comment(subject, student, comment);
            this.commentDAO.save(toSaveComment);
            response = new CommentView(student.getFirstName(), student.getSecondName(), comment, new ArrayList<>(),
                    toSaveComment.getId(), toSaveComment.getDate(), toSaveComment.getHour());
        }
        return response;
    }

    /* a gente apagou o clone */
    public CommentView addSubcommentToSubject(String studentToken, long commentId, long idSubject, JSONObject request) {
        CommentView response = null;

        Comment comment = this.commentDAO.findById(commentId);

        Student superCommentStudent = this.commentDAO.findById(commentId).getStudent();
        Subject superCommentSubject = this.subjectDAO.findById(idSubject);

        /* a subCommentSubject deve ser a mesma já que subcomment simula a resposta a um comentario */
        String txtComment = (String) request.get("comment");
        Student subCommentStudent = this.util.getStudent(studentToken, studentDAO);


        if (superCommentStudent != null && superCommentSubject != null && subCommentStudent != null
                && txtComment != null && !"".equals(txtComment.trim())) {

            Comment subComment = new Comment(superCommentSubject, subCommentStudent, txtComment, comment);
            comment.getSubcomments().add(subComment);
            this.commentDAO.save(subComment);
            this.commentDAO.save(comment);

            response = new CommentView(subCommentStudent.getFirstName(), subCommentStudent.getSecondName(), txtComment, new ArrayList<>(),
                    subComment.getId(), subComment.getDate(), subComment.getHour());
        }

        return response;
    }

    public void deleteComment(String token, long id) {
        Student student = this.util.getStudent(token, studentDAO);
        final Comment comment = this.commentDAO.findById(id);
        if (comment.getStudent().getEmail().equals(student.getEmail())) {
            comment.setVisible(false);
            this.commentDAO.save(comment);
        }
    }


    public List<Comment> getComments(long id) {
        Subject subject = this.subjectDAO.findById(id);
        Subject view = subject.superficialClone();
        List<Comment> comments = view.getSubjectComments();
        return comments;
    }
}
