package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.service.SubjectService;
import com.ufcg.cc.psoft.ucdb.view.SubjectProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"v1/subjects"})
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService; }

    // esse metodo aqui eh uma gambiarrra para que inicie-se o banco de dados
    @GetMapping(value = "/create/allsubjects")
    public void saveAllSubjects() throws IOException, ParseException {
        this.subjectService.saveAllSubjects();

    }

    @GetMapping(value = "/id/{id}")
    @ResponseBody
    public ResponseEntity<SubjectProfile> findById(@PathVariable long id) {
        SubjectProfile subject = subjectService.findById(id);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List> findAll() {

        List subjects = subjectService.findAll();

        return new ResponseEntity<List>(subjects, HttpStatus.OK);
    }

    @GetMapping(value = "/search/{substring}")
    public ResponseEntity<List> findBySubstring(@PathVariable String substring){
        List<SubjectProfile> subjects = subjectService.findBySubstring(substring);

        return new ResponseEntity<List>(subjects, HttpStatus.OK);
    }

    //provavelmente não vai ficar acessivel
    @DeleteMapping(value = "/")
    public ResponseEntity deleteAll() {
        subjectService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * TODO: OS METODOS VAO SER ALTERADOS PARA QUE SEJA APENAS POSSIVEL ACESSA-LOS COM O TOKEN DE AUTENTIFICACAO
     */

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @PostMapping(value = "/like")
    public void beLike(@RequestBody JSONObject request) {
        this.subjectService.like(request);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @PostMapping(value = "/unlike")
    public void beUnlike(@RequestBody JSONObject request) {
        this.subjectService.unlike(request);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @PostMapping(value = "/dislike")
    public void beDislike(@RequestBody JSONObject request) {
        this.subjectService.dislike(request);
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id" }
     */
    @PostMapping(value = "/undislike")
    public void beUndislike(@RequestBody JSONObject request) {
        this.subjectService.undislike(request);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        subjectService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    // provavelmente não vai ficar acessivel para todo mundo ver
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject responseSubject = this.subjectService.create(subject);
        return new ResponseEntity<>(responseSubject, HttpStatus.CREATED);
    }


}