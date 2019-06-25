package com.ufcg.cc.psoft.ucdb.controller;

import com.ufcg.cc.psoft.ucdb.service.EvalueService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"v1/subject/evalue="})
public class EvalueController {

    private final EvalueService evalueService;

    public EvalueController(EvalueService evalueService) {
        this.evalueService = evalueService;
    }

    /**
     * Sintaxe:
     * {"user": "email_user", "subject" : "subject_id", "evaluation" : "um double"}
     */
    @PostMapping(value = "/")
    public void evalueSubject(@RequestBody JSONObject request) {
        this.evalueService.evalueSubject(request);
    }
}
