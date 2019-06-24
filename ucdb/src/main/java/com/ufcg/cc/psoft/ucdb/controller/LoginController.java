package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.exceptions.InvalidLoginException;
import com.ufcg.cc.psoft.exceptions.NotCorrespondingUserLogin;
import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.service.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * LoginController: LoginController, this class is responsible for managing a system of user authentication
 * on the system.
 */
@RestController
@RequestMapping("v1/login")
public class LoginController {






    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

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
    public @NotNull ResponseEntity<LoginService.LoginResponse> authenticate(@RequestBody User requestUser) throws NotCorrespondingUserLogin,
            InvalidLoginException {
        return this.loginService.getLoginResponseEntity(requestUser);
    }

}


