package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.exceptions.InvalidLoginException;
import com.ufcg.cc.psoft.exceptions.NotCorrespondingStudentLogin;
import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.view.StudentView;
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
    private StudentService studentService;

    @NotNull
    public ResponseEntity<LoginResponse> getLoginResponseEntity(@RequestBody Student requestStudent)
            throws NotCorrespondingStudentLogin {
        String email = requestStudent.getEmail();
        String password= requestStudent.getPassword();

        if (isInvalidValue(email) || isInvalidValue(password)) {
            throw new InvalidLoginException("Os valores passados na autentificação não correspondem a nenhum " +
                    "dos usuarios do banco de dados");
        }

        StudentView registeredStudent = studentService.findByLogin(email,  password);
        if(registeredStudent == null)
            throw  new NotCorrespondingStudentLogin("Student not found");

        String studentToken = getToken(registeredStudent.getEmail());
        return new ResponseEntity<>(new LoginResponse(studentToken), HttpStatus.OK);
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
     * @param studentRepresentationToToken : toString of user concat with toString of current date
     * @return : a token authorization
     */
    private String getToken(String studentRepresentationToToken) {
        return  Jwts.builder().
                setSubject(studentRepresentationToToken).
                signWith(SignatureAlgorithm.HS512, TOKEN_KEY).
                setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).
                compact();
    }


    /**
     * Simple object representing the login response
     */
    public class LoginResponse {
        /** Student authorized token*/
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }
}
