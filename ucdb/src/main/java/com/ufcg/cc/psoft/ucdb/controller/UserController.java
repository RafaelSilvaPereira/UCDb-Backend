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

        if (responseUser == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
        }
    }

    /* essa utl privada poderia ser qualquer outra "/carambolas" */
    @PostMapping(value = "/enjoyed/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> enjoyeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.userService.enjoyed(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }

    @PostMapping(value = "/disliked/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> dislikeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.userService.disliked(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }
}
