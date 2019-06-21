package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import com.ufcg.cc.psoft.ucdb.service.SubjectService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    @GetMapping(value = "/id/{id}")
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

    @GetMapping(value = "/search/{substring}")
    public ResponseEntity<List> findBySubstring(@PathVariable String substring){
        List<Subject> subjects = subjectService.findBySubstring(substring);

        return new ResponseEntity<List>(subjects,HttpStatus.OK);
    }


    @GetMapping(value = "/create/allsubjects")
    public void saveAllSubjects() throws IOException, ParseException {
        JSONArray jsonArray;
        JSONParser jsonParser = new JSONParser();
        FileReader archive = new FileReader("src/main/java/com/ufcg/cc/psoft/util/disciplina.json");
        jsonArray = (JSONArray) jsonParser.parse(archive);
        jsonArray.stream().forEach(object -> {
            JSONObject jsonobjectvalue = (JSONObject) object;
            String name = (String) jsonobjectvalue.get("nome");
            this.createSubject(new Subject(name));
        });

    }
//    @GetMapping("/search")
//    public ResponseEntity<List> findBySubstring(@RequestParam(name = "substring", required = true) String substring) {
//        List subjects = subjectService.findBySubstring(substring);
//
//        return new ResponseEntity<List>(subjects,HttpStatus.OK);
//    }
}