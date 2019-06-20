package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SubjectDAO<T,ID extends Serializable> extends JpaRepository<Subject, Long> {

    List<Subject> findAll();

    Subject save(Subject subject);

    Subject findById(long id);

    @Query(value = "SELECT s FROM Subject s WHERE s.name LIKE concat('%',:substring,'%')")
    List<Subject> findBySubstring(@Param("substring") String substring);


    // ...
}
