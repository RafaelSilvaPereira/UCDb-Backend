package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserEvalueSubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.model.UserEvalueSubject;
import com.ufcg.cc.psoft.util.Util;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvalueService {


    private final UserEvalueSubjectDAO userEvalueSubjectDAO;

    @Autowired
    private final SubjectDAO subjectDAO;

    @Autowired
    private final UserDAO userDAO;


    private final Util util;

    public EvalueService(UserEvalueSubjectDAO userEvalueSubjectDAO, SubjectDAO subjectDAO, UserDAO userDAO) {
        this.userEvalueSubjectDAO = userEvalueSubjectDAO;
        this.subjectDAO = subjectDAO;
        this.userDAO = userDAO;
        this.util = new Util();
    }

    public void evalueSubject(JSONObject request) {
        User user = this.util.getUser(request, "user", userDAO);
        Subject subject = this.util.getSubject(request, "subject", subjectDAO);
        Double note = Double.parseDouble((String) request.get("evaluation"));

        if (user != null && subject != null && note != null && note >= 0) {
            UserEvalueSubject userEvalue = new UserEvalueSubject(subject, user, note);
            userEvalueSubjectDAO.save(userEvalue);
        }
    }
}