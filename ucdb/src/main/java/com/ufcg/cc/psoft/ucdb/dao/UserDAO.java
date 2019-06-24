package com.ufcg.cc.psoft.ucdb.dao;

import com.ufcg.cc.psoft.ucdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO<T, ID extends Serializable>  extends JpaRepository<User, String> {
    List<User> findAll();

    User save(User user);

    @Query(value = "Select u from User u where u.email=:useremail")
    User userFindByEmail(@Param("useremail") String email);

    @Query(value = "Select u from User u where u.email=:useremail and u.password=:userpassword")
    User findByLogin(@Param("useremail") String email, @Param("userpassword") String password);


}
