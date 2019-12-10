package ru.cnv.sample.vm.base;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    public static final long NETWORK_LOADING_TIMEOUT = 5000;

    private boolean isInitiated;

    private CompositeDisposable compositeDisposable;

    public BaseViewModel() {
        compositeDisposable = new CompositeDisposable();
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public boolean isInitiated() {
        return isInitiated;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }

    @WorkerThread
    public void init() {
        isInitiated = true;
    }
}
