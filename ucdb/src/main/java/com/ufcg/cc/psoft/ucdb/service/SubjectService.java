package com.ufcg.cc.psoft.ucdb.service;

import com.ufcg.cc.psoft.ucdb.dao.SubjectDAO;
import com.ufcg.cc.psoft.ucdb.model.Subject;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectService(SubjectDAO subjectDAO) {this.subjectDAO = subjectDAO; }

    public Subject create(Subject subject) { return subjectDAO.save(subject);}

    // ...

}
