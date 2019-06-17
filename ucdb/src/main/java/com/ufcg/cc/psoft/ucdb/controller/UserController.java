package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"v1/users"})
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // aqui vao ficar verificacoes para ver se o user passado eh mesmo um com tipo correto
        User responseUser = this.userService.create(user);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/private")/* essa utl privada poderia ser qualquer outra "/carambolas" */
    @ResponseBody
    public ResponseEntity<String> teste1() {
        return new ResponseEntity<>("exemplo do como funciona um metodo privado", HttpStatus.OK);
    }
}
