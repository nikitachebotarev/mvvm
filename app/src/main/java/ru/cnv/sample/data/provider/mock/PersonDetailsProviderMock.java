package ru.cnv.sample.data.provider.mock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.PersonDetailsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.PersonWithSpecs;
import ru.cnv.sample.data.provider.entity.Spec;

public class PersonDetailsProviderMock extends BaseProvider implements PersonDetailsProvider {

    public PersonDetailsProviderMock(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<PersonWithSpecs> getPersonWithSpecsById(int id) {
        return Observable.just(new PersonWithSpecs())
                .map(personWithSpecs -> {
                    Person person = new Person();
                    person.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("10-02-1993").getTime());
                    person.setFirstName("Алексей");
                    person.setLastName("Петренко");
                    person.setId(0);

                    ArrayList<Spec> specs = new ArrayList<>();
                    Spec spec = new Spec();
                    spec.setId(0);
                    spec.setName("Менеджер");
                    specs.add(spec);
                    personWithSpecs.setPerson(person);
                    personWithSpecs.setSpecs(specs);
                    return personWithSpecs;
                });
    }
}
