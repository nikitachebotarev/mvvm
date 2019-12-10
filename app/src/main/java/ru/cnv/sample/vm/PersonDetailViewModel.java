package ru.cnv.sample.vm;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.cnv.App;
import ru.cnv.sample.data.provider.entity.PersonWithSpecs;
import ru.cnv.sample.data.scheduler.DataSchedulers;
import ru.cnv.sample.vm.base.BaseViewModel;
import ru.cnv.sample.data.provider.abs.PersonDetailsProvider;

public class PersonDetailViewModel extends BaseViewModel {

    @Inject
    PersonDetailsProvider personDetailsProvider;

    @Override
    public void init() {
        super.init();
        App.getInstance().getAppComponent().inject(this);
    }

    public Observable<PersonWithSpecs> getPerson(int id) {
        return personDetailsProvider.getPersonWithSpecsById(id)
                .subscribeOn(DataSchedulers.getDataScheduler())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new PersonWithSpecs();
                });
    }
}
