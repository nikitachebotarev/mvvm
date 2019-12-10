package ru.cnv.sample.data.provider.mock;

import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.PersonsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Person;

public class PersonsProviderMock extends BaseProvider implements PersonsProvider {

    public PersonsProviderMock(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<List<Person>> getPersonsBySpecId(int id) {
        return Observable.range(0, 10)
                .map(integer -> {
                    Person person = new Person();
                    person.setId(integer);
                    person.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("10-02-1993").getTime());
                    person.setFirstName("Алексей");
                    person.setLastName("Петренко");
                    return person;
                })
                .toList()
                .toObservable();
    }

    @Override
    public Observable<List<Person>> getAllPersons() {
        return Observable.range(0, 10)
                .map(integer -> {
                    Person person = new Person();
                    person.setId(integer);
                    person.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse("10-02-1993").getTime());
                    person.setFirstName("Алексей");
                    person.setLastName("Петренко");
                    return person;
                })
                .toList()
                .toObservable();
    }

    @Override
    public Observable<String> getSpecName(int id) {
        return Observable.just("Менеджер");
    }
}
