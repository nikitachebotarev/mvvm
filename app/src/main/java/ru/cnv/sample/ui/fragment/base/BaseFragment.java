package ru.cnv.sample.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import ru.cnv.sample.data.scheduler.DataSchedulers;

public abstract class BaseFragment<T extends BaseFragmentListener> extends Fragment {

    private T listener;
    private CompositeDisposable compositeDisposable;

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this, view);

        compositeDisposable.add(Observable
                .fromCallable(() -> {
                    initViewModels();
                    return new Object();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(DataSchedulers.getDataScheduler())
                .subscribe(o -> onViewModelsInitiated(), Throwable::printStackTrace));

    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = ((T) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        compositeDisposable.clear();
    }

    public T getListener() {
        return listener;
    }

    protected abstract @LayoutRes int getLayout();

    protected abstract void initViewModels();

    protected abstract void onViewModelsInitiated();
}
