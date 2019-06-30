package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.model.Student;
import com.ufcg.cc.psoft.ucdb.service.StudentService;
import com.ufcg.cc.psoft.ucdb.view.StudentView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@Api(value = "Controller de Usuarios")
@RestController
@RequestMapping({"v1/students"})
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "Cria um novo usuario (estudante), passados por JSON: email, firstName, secondName, password")
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
    @ApiOperation(value = "Verifica se o usuario logado deu like em determinada disciplina, a partir do id da mesma")
    @GetMapping(value = "/enjoyed/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> enjoyeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.studentService.enjoyed(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }

    @ApiOperation(value = "Verifica se o usuario logado deu dislike em determinada disciplina, a partir do id da mesma")
    @GetMapping(value = "/disliked/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> dislikeds(@RequestHeader ("Authorization") String token, @PathVariable long id) {
        final Boolean enjoyed = this.studentService.disliked(token.substring(7), id);
        return new ResponseEntity<>(enjoyed, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna o JSON do usuario logado")
    @GetMapping(value = "/find")
    public ResponseEntity<StudentView> find(@RequestHeader ("Authorization") String token) {
        StudentView student = this.studentService.findStundent(token.substring(7));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
