package ru.cnv.sample.data.realm.entity;

import io.realm.RealmObject;

public class RealmSpec extends RealmObject {

    public static final String ID = "id";
    private int id;

    public static final String NAME = "name";
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
