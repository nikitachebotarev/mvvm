package ru.cnv.sample.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.cnv.App;
import ru.cnv.sample.data.provider.abs.UpdateProvider;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.scheduler.DataSchedulers;
import ru.cnv.sample.vm.base.BaseViewModel;
import ru.cnv.sample.data.provider.abs.PersonsProvider;

public class PersonsViewModel extends BaseViewModel {

    @Inject
    PersonsProvider personsProvider;

    @Inject
    UpdateProvider updateProvider;

    @Override
    public void init() {
        super.init();
        App.getInstance().getAppComponent().inject(this);
    }

    public Observable<List<Person>> getAllPersons() {
        return personsProvider.getAllPersons()
                .subscribeOn(DataSchedulers.getDataScheduler())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    public Observable<List<Person>> getAllPersonsWithUpdate() {
        return Observable.just(new Object())
                .flatMap(o -> updateProvider.update()
                        .subscribeOn(DataSchedulers.getDataScheduler())
                        .timeout(NETWORK_LOADING_TIMEOUT, TimeUnit.MILLISECONDS))
                .flatMap(o -> getAllPersons())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    public Observable<List<Person>> getPersonsBySpecId(int id) {
        return personsProvider.getPersonsBySpecId(id)
                .subscribeOn(DataSchedulers.getDataScheduler())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    public Observable<List<Person>> getPersonsBySpecIdWithUpdate(int id) {
        return Observable.just(new Object())
                .flatMap(o -> updateProvider.update()
                        .subscribeOn(DataSchedulers.getDataScheduler())
                        .timeout(NETWORK_LOADING_TIMEOUT, TimeUnit.MILLISECONDS))
                .flatMap(o -> getPersonsBySpecId(id))
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    public Observable<String> getSpecName(int id) {
        return personsProvider.getSpecName(id)
                .subscribeOn(DataSchedulers.getDataScheduler())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                });
    }
}
