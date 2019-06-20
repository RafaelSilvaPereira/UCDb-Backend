package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.exceptions.InvalidLoginException;
import com.ufcg.cc.psoft.exceptions.NotCorrespondingUserLogin;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * LoginController: LoginController, this class is responsible for managing a system of user authentication
 * on the system.
 */
@RestController
@RequestMapping("v1/login")
public class LoginController {

    /** Private system key */
    private final String TOKEN_KEY = "HAPPY_TOMATO";


    @Autowired
    private UserService userService;

    /**
     * From a user is generated an authorization token, if the user of the request is a user already registered.
     * @param requestUser : is a user created for the post method.
     * @return :  an authorization token for the user.
     * @throws NotCorrespondingUserLogin : if the request user does not correspond to a user registered in the database.
     * @throws InvalidLoginException : if one of the parameters (email or password) is passed with an invalid value
     *  (null, or invalid string).
     */
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User requestUser) throws NotCorrespondingUserLogin,
            InvalidLoginException {
        String email = requestUser.getEmail();
        String password= requestUser.getPassword();

        if(isInvalidValue(email) || isInvalidValue(password))
            throw new InvalidLoginException("Os valores passados na autentificação não correspondem a nenhum " +
                    "dos usuarios do banco de dados");

        User registeredUser = userService.findByLogin(email,  password);

        if(registeredUser == null)
            throw  new NotCorrespondingUserLogin("User not found");

        String currentDate = new Date().toString(); /* use current data for generate unique tokens */
        String userToken = getToken(registeredUser.toString() + currentDate);

        ResponseEntity<LoginResponse> loginResponseResponseEntity = new ResponseEntity<LoginResponse>(new LoginResponse(userToken), HttpStatus.OK);
        return loginResponseResponseEntity;
    }

    /**
     * Varify if argument provided has a valid "syntax"
     * @param value : a simple string
     * @return : true if the last string is an invalid character string or a null value, false otherwise
     */
    private boolean isInvalidValue(String value) {
        return value == null || "".equals(value.trim());
    }

    /**
     * Converts a user representation on a authorization token
     * @param userRepresentationToToken : toString of user concat with toString of current date
     * @return : a token authorization
     */
    private String getToken(String userRepresentationToToken) {
        return  Jwts.builder().
                setSubject(userRepresentationToToken).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).
                compact();
    }

    /**
     * Simple object representing the login response
     */
    private class LoginResponse {
        /** User authorized token*/
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}


