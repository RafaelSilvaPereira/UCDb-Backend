package com.ufcg.cc.psoft.ucdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subject {

    private String name;
    @Id
    private long id;
}
