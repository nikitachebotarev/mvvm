package ru.cnv.sample.data.realm.entity;

import io.realm.RealmObject;

public class RealmPersonToSpec extends RealmObject {

    public static final String ID = "id";
    private int id;

    public static final String PERSON = "person";
    private int person;

    public static final String SPEC = "spec";
    private int spec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }
}
