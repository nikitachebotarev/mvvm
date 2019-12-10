package ru.cnv.sample.data.provider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.PersonDetailsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.PersonWithSpecs;
import ru.cnv.sample.data.realm.entity.RealmPerson;
import ru.cnv.sample.data.realm.entity.RealmPersonToSpec;
import ru.cnv.sample.data.realm.entity.RealmSpec;
import ru.cnv.sample.data.utils.RepoUtils;

public class PersonDetailsProviderImpl extends BaseProvider implements PersonDetailsProvider {

    public PersonDetailsProviderImpl(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<PersonWithSpecs> getPersonWithSpecsById(int id) {
        return Observable
                .fromCallable(() -> {
                    RealmPerson person = getRealm().where(RealmPerson.class).equalTo(RealmPerson.ID, id).findFirst();
                    RealmResults<RealmPersonToSpec> relations = getRealm().where(RealmPersonToSpec.class)
                            .equalTo(RealmPersonToSpec.PERSON, person.getId()).findAll();
                    List<RealmSpec> specs = new ArrayList<>();
                    for (RealmPersonToSpec relation : relations) {
                        RealmSpec spec = getRealm().where(RealmSpec.class).equalTo(RealmSpec.ID, relation.getSpec()).findFirst();
                        specs.add(spec);
                    }
                    PersonWithSpecs personWithSpecs = new PersonWithSpecs();
                    personWithSpecs.setSpecs(RepoUtils.realmToSpecs(specs));
                    personWithSpecs.setPerson(RepoUtils.realmToPerson(person));
                    return personWithSpecs;
                });
    }
}
