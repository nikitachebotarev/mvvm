package ru.cnv.sample.data.provider;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.SpecsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.data.realm.entity.RealmSpec;
import ru.cnv.sample.data.utils.RepoUtils;

public class SpecsProviderImpl extends BaseProvider implements SpecsProvider {

    public SpecsProviderImpl(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<List<Spec>> getSpecs() {
        return Observable
                .fromCallable(() -> {
                    RealmResults<RealmSpec> specs = getRealm().where(RealmSpec.class).findAll();
                    return RepoUtils.realmToSpecs(specs);
                });
    }
}
