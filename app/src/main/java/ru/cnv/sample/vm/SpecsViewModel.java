package ru.cnv.sample.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.cnv.App;
import ru.cnv.sample.data.provider.abs.UpdateProvider;
import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.data.scheduler.DataSchedulers;
import ru.cnv.sample.vm.base.BaseViewModel;
import ru.cnv.sample.data.provider.abs.SpecsProvider;

public class SpecsViewModel extends BaseViewModel {

    @Inject
    SpecsProvider specsProvider;

    @Inject
    UpdateProvider updateProvider;

    @Override
    public void init() {
        super.init();
        App.getInstance().getAppComponent().inject(this);
    }

    public Observable<List<Spec>> getSpecs() {
        return specsProvider.getSpecs()
                .subscribeOn(DataSchedulers.getDataScheduler())
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                });
    }

    public Observable<List<Spec>> getSpecsWithUpdate() {
        return Observable.just(new Object())
                .flatMap(o -> updateProvider.update()
                        .subscribeOn(DataSchedulers.getDataScheduler())
                        .timeout(NETWORK_LOADING_TIMEOUT, TimeUnit.MILLISECONDS))
                .flatMap(o -> specsProvider.getSpecs()
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return new ArrayList<>();
                }));
    }
}
