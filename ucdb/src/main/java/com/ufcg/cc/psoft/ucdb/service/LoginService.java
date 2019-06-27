package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.exceptions.InvalidLoginException;
import com.ufcg.cc.psoft.exceptions.NotCorrespondingUserLogin;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.view.UserView;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;


@Service
public class LoginService {

    /** Private system key */
    private final String TOKEN_KEY = "HAPPY_TOMATO";

    @Autowired
    private UserService userService;

    @NotNull
    public ResponseEntity<LoginResponse> getLoginResponseEntity(@RequestBody User requestUser)
            throws NotCorrespondingUserLogin {
        String email = requestUser.getEmail();
        String password= requestUser.getPassword();

        if (isInvalidValue(email) || isInvalidValue(password)) {
            throw new InvalidLoginException("Os valores passados na autentificação não correspondem a nenhum " +
                    "dos usuarios do banco de dados");
        }

        UserView registeredUser = userService.findByLogin(email,  password);
        if(registeredUser == null)
            throw  new NotCorrespondingUserLogin("User not found");

        String userToken = getToken(registeredUser.getEmail());
        return new ResponseEntity<>(new LoginResponse(userToken), HttpStatus.OK);
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
    public class LoginResponse {
        /** User authorized token*/
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}
