package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.service.StudentService;
import com.ufcg.cc.psoft.ucdb.view.StudentView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"v1/students"})
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<StudentView> createStudent(@RequestBody Student student) {
        // aqui vao ficar verificacoes para ver se o student passado eh mesmo um com tipo correto
        StudentView responseStudent = this.studentService.create(student);

        if (responseStudent == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(responseStudent, HttpStatus.CREATED);
        }
    }

    /* essa utl privada poderia ser qualquer outra "/carambolas" */
    @GetMapping(value = "/enjoyed/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> enjoyeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.studentService.enjoyed(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }

    @GetMapping(value = "/disliked/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> dislikeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.studentService.disliked(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }
}
