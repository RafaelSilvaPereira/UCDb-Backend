package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface StudentDAO<T, ID extends Serializable>  extends JpaRepository<Student, String> {
    List<Student> findAll();

    Student save(Student student);

    @Query(value = "Select u from Student u where u.email=:studentemail")
    Student studentFindByEmail(@Param("studentemail") String email);

    @Query(value = "Select u from Student u where u.email=:studentemail and u.password=:studentpassword")
    Student findByLogin(@Param("studentemail") String email, @Param("studentpassword") String password);


}
