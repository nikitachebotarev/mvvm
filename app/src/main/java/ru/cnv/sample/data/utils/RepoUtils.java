package ru.cnv.sample.data.utils;

import java.util.ArrayList;
import java.util.List;

import ru.cnv.sample.data.realm.entity.RealmPerson;
import ru.cnv.sample.data.realm.entity.RealmPersonToSpec;
import ru.cnv.sample.data.realm.entity.RealmSpec;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.PersonToSpec;
import ru.cnv.sample.data.provider.entity.Spec;

public class RepoUtils {

    public static List<Spec> realmToSpecs(List<RealmSpec> models) {
        ArrayList<Spec> specs = new ArrayList<>();
        for (RealmSpec model : models) {
            Spec spec = new Spec();
            spec.setId(model.getId());
            spec.setName(model.getName());
            specs.add(spec);
        }
        return specs;
    }

    public static List<Person> realmToPersons(List<RealmPerson> models) {
        ArrayList<Person> persons = new ArrayList<>();
        for (RealmPerson model : models) {
            persons.add(realmToPerson(model));
        }
        return persons;
    }

    public static Person realmToPerson(RealmPerson model) {
        Person person = new Person();
        person.setId(model.getId());
        person.setFirstName(model.getFirstName());
        person.setLastName(model.getLastName());
        person.setAvatar(model.getAvatar());
        person.setBirthday(model.getBirthday());
        return person;
    }

    public static List<RealmPerson> personsToRealm(List<Person> models) {
        ArrayList<RealmPerson> persons = new ArrayList<>();
        for (Person model : models) {
            persons.add(personToRealm(model));
        }
        return persons;
    }

    public static RealmPerson personToRealm(Person model) {
        RealmPerson person = new RealmPerson();
        person.setId(model.getId());
        person.setFirstName(model.getFirstName());
        person.setLastName(model.getLastName());
        person.setAvatar(model.getAvatar());
        person.setBirthday(model.getBirthday());
        return person;
    }

    public static List<RealmSpec> specsToRealm(List<Spec> models) {
        ArrayList<RealmSpec> items = new ArrayList<>();
        for (Spec model : models) {
            items.add(specToRealm(model));
        }
        return items;
    }

    public static RealmSpec specToRealm(Spec model) {
        RealmSpec item = new RealmSpec();
        item.setId(model.getId());
        item.setName(model.getName());
        return item;
    }

    public static List<RealmPersonToSpec> relationsToRealm(List<PersonToSpec> models) {
        ArrayList<RealmPersonToSpec> items = new ArrayList<>();
        for (PersonToSpec model : models) {
            items.add(relationToRealm(model));
        }
        return items;
    }

    public static RealmPersonToSpec relationToRealm(PersonToSpec model) {
        RealmPersonToSpec item = new RealmPersonToSpec();
        item.setPerson(model.getPerson());
        item.setSpec(model.getSpec());
        return item;
    }
}
