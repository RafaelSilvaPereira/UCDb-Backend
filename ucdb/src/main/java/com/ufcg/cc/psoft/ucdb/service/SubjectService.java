package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserEvalueSubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.*;
import com.ufcg.cc.psoft.ucdb.view.SubjectProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    @Autowired
    private final UserDAO userDAO;

    private final UserEvalueSubjectDAO userEvalueSubjectDAO;

    private final CommentDAO commentDAO;


    public SubjectService(SubjectDAO subjectDAO, UserDAO userDAO, UserEvalueSubjectDAO userEvalueSubjectDAO, CommentDAO commentDAO ) {
        this.subjectDAO = subjectDAO;
        this.userDAO = userDAO;
        this.userEvalueSubjectDAO = userEvalueSubjectDAO;
        this.commentDAO = commentDAO;
    }

    public Subject create(Subject subject) { return subjectDAO.save(subject);}

    public void delete(long id) { subjectDAO.delete(id);}

    public SubjectProfile findById(long id) {
        Subject superficialClone = subjectDAO.findById(id).superficialClone();
        return new SubjectProfile(superficialClone);
    }

    public List findAll() {
        final List<Subject> subjectDAOAll = subjectDAO.findAll();
        return subjectDAOAll.stream().map(subject -> new SubjectProfile(subject.superficialClone())).collect(Collectors.toList());
    }

    public List<SubjectProfile> findBySubstring(String substring) {
        String auxSubstring = reconvertValidUrlToOriginalString(substring);
        List<Subject> subjectDAOSubstring = subjectDAO.findBySubstring(auxSubstring);
        List<SubjectProfile> subjectProfiles = subjectDAOSubstring.stream().map(subject -> new SubjectProfile(subject.superficialClone())).collect(Collectors.toList());
        return subjectProfiles;
    }

    public void saveAllSubjects() throws IOException, ParseException {
        JSONArray jsonArray;
        JSONParser jsonParser = new JSONParser();
        FileReader archive = new FileReader("src/main/java/com/ufcg/cc/psoft/util/disciplina.json");
        jsonArray = (JSONArray) jsonParser.parse(archive);
        jsonArray.stream().forEach(object -> {
            JSONObject jsonobjectvalue = (JSONObject) object;
            String name = (String) jsonobjectvalue.get("nome");
            this.create(new Subject(name));
        });
    }

    public void deleteAll() { subjectDAO.deleteAll();}

    public void like(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");
        User user1 = null;
        if (user != null && !user.isNIL()) {
            user1 = new User(user.getEmail(), user.getFirstName(), user.getSecondName(), user.getPassword());
        }

        subject.getUserLiked().add(user1);
        Subject subject1 = null;
        if (subject != null && !subject.isNIL()) {
            subject1 = new Subject(subject.getId(), subject.getName());
        }

        user.getEnjoiyed().add(subject1);
        subject.getUserLiked().add(user1);
        undislike(user, subject);

        updateDataBase(user, subject);

    }

    public void unlike(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");

        unlike(user, subject);

        updateDataBase(user, subject);
    }

    public void dislike(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");

        subject.getUserDisliked().add(user);
        user.getDisliked().add(subject);

        unlike(user, subject);

        updateDataBase(user, subject);

    }

    public void undislike(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");

        undislike(user, subject);
        updateDataBase(user, subject);
    }

    public void evalueSubject(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");
        Double note = Double.parseDouble((String) request.get("evaluation"));

        if(user != null && subject != null && note != null && note >= 0) {
            UserEvalueSubject userEvalue = new UserEvalueSubject(subject, user, note);
            userEvalueSubjectDAO.save(userEvalue);
        }
    }

    public void commentSubject(JSONObject request) {
        User user = getUser(request, "user");
        Subject subject = getSubject(request, "subject");
        String comment = (String) request.get("comment");

        if (user != null && subject != null && comment != null && !"".equals(comment.trim())) {
            final Comment toSaveComment = new Comment(subject, user, comment);
            this.commentDAO.save(toSaveComment);
        }
    }

    public void addSubcommentToSubject(JSONObject request) {
        User superCommentUser = getUser(request, "superCommentUserEmail").superficialCopy();

        Subject superCommentSubject = getSubject(request, "superCommentSubjectID").superficialClone();

        User subCommentUser = getUser(request, "subCommentUserEmail").superficialCopy();
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

    private String reconvertValidUrlToOriginalString(String substring) {
        String auxSubstring = "";
        String[] strings = substring.split("_");

        for (String code : strings) {
            char a = ((char) Integer.parseInt(code));
            auxSubstring += a;
        }
        return auxSubstring;
    }

    private void undislike(User user, Subject subject) {
        subject.getUserDisliked().remove(user);
        user.getDisliked().remove(subject);
    }

    private void unlike(User user, Subject subject) {
        subject.getUserLiked().remove(user);
        user.getEnjoiyed().remove(subject);
    }

    private void updateDataBase(User user, Subject subject) {
        userDAO.save(user); // update user
        subjectDAO.save(subject);
    }

    private User getUser(JSONObject request, String value) {
        String userEmail = (String) request.get(value);
        return userDAO.userFindByEmail(userEmail);
    }

    private Subject getSubject(JSONObject request, String value) {
        String subjectId = (String) request.get(value);
        final long id = Long.parseLong(subjectId);
        return subjectDAO.findById(id);
    }

    public void deleteComment(String subjectId, String userEmail) {
        Long subjectIdLong = Long.parseLong(subjectId);
        final Comment comment = this.commentDAO.findByIdAlternative(new SubjectUserID(subjectIdLong, userEmail));
        comment.setVisible(false);
        this.commentDAO.save(comment);
    }


    // ...

}
