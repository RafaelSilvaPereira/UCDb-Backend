package com.ufcg.cc.psoft.ucdb.service;


import com.ufcg.cc.psoft.ucdb.dao.UserDAO;
import com.ufcg.cc.psoft.ucdb.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User create(User user) {
        return userDAO.save(user);
    }

    public User findByLogin(String email, String password) {
        return userDAO.findByLogin(email, password);
    }
    // TODO: Um dos findById não está funcionando corretamente  DAI EU TIVE QUE COMENTAR PARA TESTAR O RESTO

 }
