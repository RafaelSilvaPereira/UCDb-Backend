package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.service.SubjectService;
import com.ufcg.cc.psoft.ucdb.view.GenericSubjectProfile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(value = "")
@RestController
@RequestMapping({"v1/subjects"})
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService; }

    @ApiOperation(value = "Procura uma disciplina por id")
    @GetMapping(value = "/id/{id}")
    @ResponseBody
    public ResponseEntity<GenericSubjectProfile> findById(@PathVariable long id) {
        GenericSubjectProfile subject = subjectService.findById(id);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }


    /*
     * tipo de order aceitadas: likes, dislikes, comments, proportion
     * */
    @ApiOperation(value = "Retorna a lista ranqueada das disciplinas, passado o tipo de ordenacao")
    @GetMapping(value = "/sort/{order}")
    public ResponseEntity<List> findAll(@PathVariable String order) {
        List subjects = subjectService.findAll(order);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @ApiOperation(value = "Procura uma disciplina passada uma substring")
    @GetMapping(value = "/search/{substring}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List> findBySubstring(@PathVariable String substring){
        List<GenericSubjectProfile> subjects = subjectService.findBySubstring(substring);

        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    /*
     * TODO: OS METODOS VAO SER ALTERADOS PARA QUE SEJA APENAS POSSIVEL ACESSA-LOS COM O TOKEN DE AUTENTIFICACAO
     */

    /**
     * Sintaxe:
     * { "user_token":"user_token", "subject" : "subject_id" }
     */
    @ApiOperation(value = "Deixa um like em determinada disciplina, a partir do id da mesma")
    @PostMapping(value = "/like/{id}")
    public void beLike(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        this.subjectService.like(token.substring(7), id);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @ApiOperation(value = "Retira o like de determinada disciplina, a partir do id da mesma")
    @PostMapping(value = "/unlike/{id}")
    public void beUnlike(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        this.subjectService.unlike(token.substring(7), id);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @ApiOperation(value = "Deixa um dislike em determinada disciplina, a partir do id da mesma")
    @PostMapping(value = "/dislike/{id}")
    public void beDislike(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        this.subjectService.dislike(token.substring(7), id);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @ApiOperation(value = "Retira o dislike de determinada disciplina, a partir do id da mesma")
    @PostMapping(value = "/undislike/{id}")
    public void beUndislike(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        this.subjectService.undislike(token.substring(7), id);
    }
}