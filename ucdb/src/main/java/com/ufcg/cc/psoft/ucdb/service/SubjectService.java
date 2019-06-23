package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserEvalueSubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Comment;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.model.UserEvalueSubject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.FileReader;
import java.io.IOException;
import java.util.List;



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

    public Subject findById(long id) { return subjectDAO.findById(id); }

    public List findAll() { return subjectDAO.findAll(); }

    public List<Subject> findBySubstring(String substring) {
        String auxSubstring = reconvertValidUrlToOriginalString(substring);
        return subjectDAO.findBySubstring(auxSubstring);
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
        User user = getUser(request);
        Subject subject = getSubject(request);

        subject.getUserLiked().add(user);
        user.getEnjoiyed().add(subject);

        undislike(user, subject);

        updateDataBase(user, subject);

    }

    public void unlike(JSONObject request) {
        User user = getUser(request);
        Subject subject = getSubject(request);

        unlike(user, subject);

        updateDataBase(user, subject);
    }

    public void dislike(JSONObject request) {
        User user = getUser(request);
        Subject subject = getSubject(request);

        subject.getUserDisliked().add(user);
        user.getDisliked().add(subject);

        unlike(user, subject);

        updateDataBase(user, subject);

    }

    public void undislike(JSONObject request) {
        User user = getUser(request);
        Subject subject = getSubject(request);

        undislike(user, subject);
        updateDataBase(user, subject);
    }

    public void evalueSubject(JSONObject request) {
        User user = getUser(request);
        Subject subject = getSubject(request);
        Double note = (Double) request.get("Note");

        if(user != null && subject != null && note != null && note >= 0) {
            userEvalueSubjectDAO.save(new UserEvalueSubject(subject, user, note));
        }
    }

    public void commentSubject(JSONObject request) {
        User user = getUser(request);
        Subject subject = getSubject(request);
        String comment = (String) request.get("comment");

        if(user != null && subject != null &&comment != null && !"".equals(comment.trim())) {
            this.commentDAO.save(new Comment(subject, user, comment));
        }
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

    private User getUser(JSONObject request) {
        String userEmail = (String) request.get("userEmail");
        return userDAO.userFindByEmail(userEmail);
    }

    private Subject getSubject(JSONObject request) {
        String subjectId = (String) request.get("subjectId");
        return subjectDAO.findById(Integer.parseInt(subjectId));
    }




    // ...

}
