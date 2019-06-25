package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.view.UserView;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserView create(User user) {
        User copy = userDAO.save(user);
        return new UserView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
    }

    public UserView findByLogin(String email, String password) {
        User copy = userDAO.findByLogin(email,password);
        return new UserView(copy.getEmail(),copy.getFirstName(),copy.getSecondName());
    }
    // TODO: Um dos findById não está funcionando corretamente  DAI EU TIVE QUE COMENTAR PARA TESTAR O RESTO

 }
