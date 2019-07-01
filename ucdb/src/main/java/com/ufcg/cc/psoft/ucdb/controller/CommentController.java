package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.service.CommentService;
import com.ufcg.cc.psoft.ucdb.view.CommentView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Controller de Comentarios")
@RestController
@RequestMapping({"v1/comment"})
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Sintaxe:
     * { "user":"email_user", "subject" : "subject_id", "comment" : "comentario"}
     * @return
     */
    @ApiOperation(value = "Cria um novo comentario em uma disciplina, passado o id da mesma. JSON: comment")
    @PostMapping(value = "/create/{id}")
    public ResponseEntity commentSubject(@RequestHeader("Authorization") String token, @PathVariable long id, @RequestBody JSONObject request) {
        final CommentView commentView = this.commentService.commentSubject(token.substring(7), id, request);
        System.out.println(commentView);
        return new ResponseEntity<>(commentView, HttpStatus.OK);
    }

    /**
     * Sintaxe:
     * {
     * "superCommentUserEmail" : "email_do_usuario_do_comentario_pai"
     * "superCommentSubjectID" : "id_da_disciplina_do_comentario_pai"
     * "subCommentUserEmail" : "email_do_usuario_do_comentario_filho"
     * "comment": "a resposta do comentario"
     * }
     * @return
     */
    @ApiOperation(value = "Responde um comentario, passado o id da disciplina e do comentario. JSON: comment")
    @PostMapping(value = "/reply/{idSubject}/{idComment}")
    public ResponseEntity<CommentView> addSubcommentToSubject(@RequestHeader("Authorization") String token, @PathVariable long idSubject,
                                                              @PathVariable long idComment, @RequestBody JSONObject request) {
        return new ResponseEntity<>(this.commentService.addSubcommentToSubject(token.substring(7), idComment, idSubject, request), HttpStatus.OK);
    }

    @ApiOperation(value = "Deleta um comentario, passado o id do mesmo")
    @DeleteMapping(value = "/{id}") /*o id do comentario*/
    public ResponseEntity<Boolean> delete(@RequestHeader("Authorization") String token, @PathVariable long id) {
        this.commentService.deleteComment(token.substring(7), id);
        return new ResponseEntity(new Boolean(true), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna todos comentarios de uma disciplina, passado o id da mesma")
    @GetMapping(value = "/subject_comments/{id}")
    public ResponseEntity<List> getComments(@PathVariable long id) {
        List comments = this.commentService.getComments(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
