package ru.cnv.sample.data.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.api.entity.PersonsResponse;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.pref.entity.UpdateInfo;
import ru.cnv.sample.data.provider.abs.UpdateProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.PersonToSpec;
import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.data.realm.entity.RealmPerson;
import ru.cnv.sample.data.realm.entity.RealmPersonToSpec;
import ru.cnv.sample.data.realm.entity.RealmSpec;
import ru.cnv.sample.data.utils.RepoUtils;
import ru.cnv.sample.vm.utils.VmUtils;

public class UpdateProviderImpl extends BaseProvider implements UpdateProvider {

    private static final long UPDATE_TIME_PERIOD_MILLIS = 0;

    public UpdateProviderImpl(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    public Observable<Object> update() {
        return Observable
                .fromCallable(() -> {
                    UpdateInfo updateInfo = getUpdateInfo(getPreferenceManager());
                    if (updateInfo != null && new Date().getTime() < updateInfo.getLastUpdateTime() + UPDATE_TIME_PERIOD_MILLIS) {
                        return new Object();
                    }

                    PersonsResponse personsResponse = getRetrofitService().getPersons().execute().body();
                    for (int i = 0; i < personsResponse.getItems().size(); i++) {
                        personsResponse.getItems().get(i).setId(i);
                    }

                    List<Person> people = new ArrayList<>();
                    for (PersonsResponse.Item item : personsResponse.getItems()) {
                        people.add(VmUtils.personResponseToPerson(item));
                    }

                    List<PersonsResponse.Item.Speciality> allSpecs = new ArrayList<>();
                    for (PersonsResponse.Item item : personsResponse.getItems()) {
                        allSpecs.addAll(item.getSpec());
                    }

                    List<Spec> uniqueSpecs = new ArrayList<>();
                    for (PersonsResponse.Item.Speciality speciality : allSpecs) {
                        boolean isContains = false;
                        for (Spec addedSpec : uniqueSpecs) {
                            if (addedSpec.getName().equals(speciality.getName())) {
                                isContains = true;
                                break;
                            }
                        }
                        if (!isContains) {
                            uniqueSpecs.add(VmUtils.responseToSpec(speciality));
                        }
                    }

                    List<PersonToSpec> relations = new ArrayList<>();
                    for (PersonsResponse.Item item : personsResponse.getItems()) {
                        for (PersonsResponse.Item.Speciality speciality : item.getSpec()) {
                            PersonToSpec personToSpec = new PersonToSpec();
                            personToSpec.setPerson(item.getId());
                            personToSpec.setSpec(speciality.getId());
                            relations.add(personToSpec);
                        }
                    }

                    getRealm().executeTransaction(db -> {
                        db.delete(RealmPerson.class);
                        db.delete(RealmSpec.class);
                        db.delete(RealmPersonToSpec.class);
                        db.insert(RepoUtils.personsToRealm(people));
                        db.insert(RepoUtils.specsToRealm(uniqueSpecs));
                        db.insert(RepoUtils.relationsToRealm(relations));
                    });

                    saveUpdateInfo(getPreferenceManager());
                    return new Object();
                });
    }

    private void saveUpdateInfo(PreferenceManager preference) throws IOException {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setLastUpdateTime(new Date().getTime());
        preference.saveObject(updateInfo, PreferenceManager.KEY_UPDATE_INFO);
    }

    private UpdateInfo getUpdateInfo(PreferenceManager preference) throws IOException {
        return preference.getObject(UpdateInfo.class, PreferenceManager.KEY_UPDATE_INFO);
    }
}
