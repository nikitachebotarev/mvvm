package ru.cnv.sample.data.provider.entity;

import java.util.List;

public class PersonWithSpecs {

    private Person person;
    private List<Spec> specs;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }
}
