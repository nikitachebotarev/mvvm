package ru.cnv.sample.data.realm.entity;

import io.realm.RealmObject;

public class RealmPerson extends RealmObject {

    public static final String ID = "id";
    private int id;

    public static final String FIRST_NAME = "first_name";
    private String firstName;

    public static final String LAST_NAME = "last_name";
    private String lastName;

    public static final String BIRTHDAY = "birthday";
    private long birthday;

    public static final String AVATAR = "avatar";
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
