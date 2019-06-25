package com.ufcg.cc.psoft.util;

import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import org.json.simple.JSONObject;

public class Util {

    public User getUser(JSONObject request, String value, UserDAO userDAO) {
        String userEmail = (String) request.get(value);
        return userDAO.userFindByEmail(userEmail);
    }

    public Subject getSubject(JSONObject request, String value, SubjectDAO subjectDAO) {
        String subjectId = (String) request.get(value);
        final long id = Long.parseLong(subjectId);
        return subjectDAO.findById(id);
    }

    public String reconvertValidUrlToOriginalString(String substring) {
        String auxSubstring = "";
        String[] strings = substring.split("_");

        for (String code : strings) {
            char a = ((char) Integer.parseInt(code));
            auxSubstring += a;
        }
        return auxSubstring;
    }
}
