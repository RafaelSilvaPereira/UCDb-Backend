package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.User;
import com.ufcg.cc.psoft.ucdb.service.UserService;
import com.ufcg.cc.psoft.ucdb.view.UserView;
import org.json.simple.JSONObject;
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
    public ResponseEntity<UserView> createUser(@RequestBody User user) {
        // aqui vao ficar verificacoes para ver se o user passado eh mesmo um com tipo correto
        UserView responseUser = this.userService.create(user);
        return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
    }

    /* essa utl privada poderia ser qualquer outra "/carambolas" */
    @PostMapping(value = "/enjoyed")
    @ResponseBody
    public ResponseEntity<Boolean> enjoyeds(@RequestBody JSONObject request) {
        final Boolean enjoyed = this.userService.enjoyed(request);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }

    @PostMapping(value = "/disliked")
    @ResponseBody
    public ResponseEntity<Boolean> dislikeds(@RequestBody JSONObject request) {
        final Boolean enjoyed = this.userService.disliked(request);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }
}
