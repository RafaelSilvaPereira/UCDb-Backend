package com.ufcg.cc.psoft.ucdb.view;

public class GenericSubjectProfile {
    protected Long id;
    protected String name;

    public GenericSubjectProfile(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenericSubjectProfile() {

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GenericSubjectProfile toGenericRepresentation() {
        return new GenericSubjectProfile(this.id, this.name);
    }
}
