package ru.cnv.sample.data.provider.mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.abs.SpecsProvider;
import ru.cnv.sample.data.provider.base.BaseProvider;
import ru.cnv.sample.data.provider.entity.Spec;

public class SpecsProviderMock extends BaseProvider implements SpecsProvider {

    public SpecsProviderMock(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        super(retrofitService, preferenceManager, realm);
    }

    @Override
    public Observable<List<Spec>> getSpecs() {
        return Observable.just(new Object())
                .map(o -> {
                    Spec m = new Spec();
                    m.setId(0);
                    m.setName("Менеджер");

                    Spec d = new Spec();
                    d.setName("Разработчик");
                    d.setId(1);

                    Spec t = new Spec();
                    t.setName("Тестеровщик");
                    t.setId(2);

                    ArrayList<Spec> specs = new ArrayList<>();
                    specs.add(m);
                    specs.add(d);
                    specs.add(t);
                    return specs;
                });
    }
}
