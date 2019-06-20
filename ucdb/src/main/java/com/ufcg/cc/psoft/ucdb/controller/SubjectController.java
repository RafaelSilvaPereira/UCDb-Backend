package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"v1/subjects"})
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) { this.subjectService = subjectService; }

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject responseSubject = this.subjectService.create(subject);
        return  new ResponseEntity<>(responseSubject, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity deleteAll() {
        subjectService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        subjectService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Subject> findById(@PathVariable long id) {
        Subject subject = subjectService.findById(id);

        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List> findAll() {
        List subjects = subjectService.findAll();

        return new ResponseEntity<List>(subjects,HttpStatus.OK);
    }

//    @GetMapping(value = "/search={substring}")
//    public ResponseEntity<List> findBySubstring(@PathVariable String substring){
//        List subjects = subjectService.findBySubstring(substring);
//
//        return new ResponseEntity<List>(subjects,HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List> findBySubstring(@RequestParam(name = "substring", required = true) String substring) {
        List subjects = subjectService.findBySubstring(substring);

        return new ResponseEntity<List>(subjects,HttpStatus.OK);
    }
}