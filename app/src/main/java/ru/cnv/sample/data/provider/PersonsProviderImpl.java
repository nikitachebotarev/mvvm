package ru.cnv.sample.data.provider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.PersonsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.realm.entity.RealmPerson;
import ru.cnv.sample.data.realm.entity.RealmPersonToSpec;
import ru.cnv.sample.data.realm.entity.RealmSpec;
import ru.cnv.sample.data.utils.RepoUtils;

public class PersonsProviderImpl extends BaseProvider implements PersonsProvider {

    public PersonsProviderImpl(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<List<Person>> getPersonsBySpecId(int id) {
        return Observable
                .fromCallable(() -> {
                    RealmResults<RealmPersonToSpec> relations = getRealm().where(RealmPersonToSpec.class).equalTo(RealmPersonToSpec.SPEC, id).findAll();
                    List<RealmPerson> people = new ArrayList<>();
                    for (RealmPersonToSpec relation : relations) {
                        RealmPerson person = getRealm().where(RealmPerson.class).equalTo(RealmPerson.ID, relation.getPerson()).findFirst();
                        people.add(person);
                    }
                    return RepoUtils.realmToPersons(people);
                });
    }

    @Override
    public Observable<List<Person>> getAllPersons() {
        return Observable
                .fromCallable(() -> {
                    List<RealmPerson> persons = getRealm().where(RealmPerson.class).findAll();
                    return RepoUtils.realmToPersons(persons);
                });
    }

    @Override
    public Observable<String> getSpecName(int id) {
        return Observable
                .fromCallable(() -> {
                    return getRealm().where(RealmSpec.class).equalTo(RealmSpec.ID, id).findFirst().getName();
                });
    }
}
