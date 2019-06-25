package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.service.CommentService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     */
    @PostMapping(value = "/create")
    public void commentSubject(@RequestBody JSONObject request) {
        this.commentService.commentSubject(request);
    }

    /**
     * Sintaxe:
     * {
     * "superCommentUserEmail" : "email_do_usuario_do_comentario_pai"
     * "superCommentSubjectID" : "id_da_disciplina_do_comentario_pai"
     * "subCommentUserEmail" : "email_do_usuario_do_comentario_filho"
     * "comment": "a resposta do comentario"
     * }
     */
    @PostMapping(value = "/reply")
    public void addSubcommentToSubject(@RequestBody JSONObject request) {
        this.commentService.addSubcommentToSubject(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        this.commentService.deleteComment(id.split("_")[1], id.split("_")[0]);
        return new ResponseEntity(HttpStatus.OK);
    }
}
