package com.ufcg.cc.psoft.ucdb;

import com.google.gson.Gson;
import com.ufcg.cc.psoft.ucdb.service.SubjectService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private SubjectService subjectService;

    @Autowired
    public DatabaseLoader(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void run(ApplicationArguments args) throws IOException, ParseException {
        this.createSubjects();
    }

    public void createSubjects() throws IOException, ParseException {
        this.subjectService.saveAllSubjects();
    }


}
