package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.view.UserView;
import com.ufcg.cc.psoft.util.Util;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;


    private final Util util;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.util = new Util();
    }

    public UserView create(User user) {
        User copy = userDAO.save(user);
        return new UserView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
    }

    public UserView findByLogin(String email, String password) {
        User copy = userDAO.findByLogin(email,password);
        return new UserView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
    }

    public Boolean enjoyed(String token, long subjectID) {
        boolean answer = false;
        if (subjectID != 0) {
            final User user = this.util.getUser(token, this.userDAO);
            for (Subject s : user.getEnjoiyed()) {
                if (s.getId() == subjectID) {
                    answer = true;
                    break;
                } else {
                    answer = false;
                }

            }
        }
        return answer;
    }

    public Boolean disliked(String token, long subjectID) {
        boolean answer = false;
        if (subjectID != 0) {
            final User user = this.util.getUser(token, this.userDAO);
            for (Subject s : user.getDisliked()) {
                if (s.getId() == subjectID) {
                    answer = true;
                    break;
                } else {
                    answer = false;
                }

            }
        }
        return answer;
    }
    // TODO: Um dos findById não está funcionando corretamente  DAI EU TIVE QUE COMENTAR PARA TESTAR O RESTO

 }
