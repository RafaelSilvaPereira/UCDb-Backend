package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.SubjectUserID;
import com.ufcg.cc.psoft.ucdb.model.UserEvalueSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserEvalueSubjectDAO<T,ID extends Serializable> extends JpaRepository<UserEvalueSubject, SubjectUserID> {

    UserEvalueSubject save(UserEvalueSubject userEvalueSubject);

    void delete(UserEvalueSubject userEvalueSubject);
}
