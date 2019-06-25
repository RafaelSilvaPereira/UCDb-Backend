package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.CommentDAO;
import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserEvalueSubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.view.SubjectProfile;
import com.ufcg.cc.psoft.util.Util;
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

    private final Util util;

    public SubjectService(SubjectDAO subjectDAO, UserDAO userDAO, UserEvalueSubjectDAO userEvalueSubjectDAO, CommentDAO commentDAO ) {
        this.subjectDAO = subjectDAO;
        this.userDAO = userDAO;
        this.userEvalueSubjectDAO = userEvalueSubjectDAO;
        this.util = new Util();
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
        String auxSubstring = this.util.reconvertValidUrlToOriginalString(substring);
        List<Subject> subjectDAOSubstring = subjectDAO.findBySubstring(auxSubstring);
        List<SubjectProfile> subjectProfiles = subjectDAOSubstring.stream().map(subject -> new SubjectProfile(subject.superficialClone())).collect(Collectors.toList());
        return subjectProfiles;
    }

    public void saveAllSubjects() throws IOException, ParseException {
        JSONArray jsonArray;
        JSONParser jsonParser = new JSONParser();
        FileReader archive = new FileReader("src/main/java/com/ufcg/cc/psoft/Util/disciplina.json");
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
        User user = this.util.getUser(request, "user", userDAO);
        Subject subject = this.util.getSubject(request, "subject", subjectDAO);

        unlike(user, subject);

        updateDataBase(user, subject);
    }

    public void dislike(JSONObject request) {
        User user = this.util.getUser(request, "user", userDAO);
        Subject subject = this.util.getSubject(request, "subject", subjectDAO);

        subject.getUserDisliked().add(user);
        user.getDisliked().add(subject);

        unlike(user, subject);

        updateDataBase(user, subject);

    }

    public void undislike(JSONObject request) {
        User user = this.util.getUser(request, "user", userDAO);
        Subject subject = this.util.getSubject(request, "subject", subjectDAO);

        undislike(user, subject);
        updateDataBase(user, subject);
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


}
