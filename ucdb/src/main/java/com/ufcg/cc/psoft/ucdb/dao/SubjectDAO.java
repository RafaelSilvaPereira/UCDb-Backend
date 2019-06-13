package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SubjectDAO<T, ID extends Serializable> extends JpaRepository<Subject,Long> {

    Subject save(Subject subject);

    Subject findById(Long id);

    List<Subject> findAll();
//    findAll(Sort)
    List<Subject> findAllById(List<Long> ids);
//    saveAll(Iterable<S>)
//    flush()
//    saveAndFlush(S)
//    deleteInBatch(Iterable<T>)
//    deleteAllInBatch()
    Subject getOne(Long id);
//    findAll(Example<S>)
//    findAll(Example<S>, Sort)

}
