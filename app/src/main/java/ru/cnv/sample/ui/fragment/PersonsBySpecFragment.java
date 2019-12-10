package ru.cnv.sample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.cnv.App;
import ru.cnv.sample.R;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.ui.adapter.PersonsHolder;
import ru.cnv.sample.ui.adapter.base.RecyclerAdapter;
import ru.cnv.sample.ui.entity.PersonItem;
import ru.cnv.sample.ui.fragment.base.BaseFragment;
import ru.cnv.sample.ui.fragment.base.BaseFragmentListener;
import ru.cnv.sample.ui.utils.UiUtils;
import ru.cnv.sample.vm.PersonsViewModel;

public class PersonsBySpecFragment extends BaseFragment<PersonsBySpecFragment.Listener> {

    public static final String ARG_ID = "id";

    PersonsViewModel personsViewModel;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.persons_rv)
    RecyclerView personsRv;

    @BindView(R.id.label_tv)
    TextView labelTv;

    @BindView(R.id.back_iv)
    ImageView backIv;

    @BindView(R.id.empty_tv)
    TextView emptyTv;

    @Override
    protected int getLayout() {
        return R.layout.fragment_persones_by_spec;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personsViewModel = ViewModelProviders.of(this).get(PersonsViewModel.class);
    }

    @Override
    @WorkerThread
    protected void initViewModels() {
        if (!personsViewModel.isInitiated()) {
            personsViewModel.init();
        }
    }

    @Override
    protected void onViewModelsInitiated() {
        if (getArguments() == null) {
            return;
        }

        int id = getArguments().getInt(ARG_ID);

        getCompositeDisposable().add(RxView.clicks(backIv)
                .subscribe(o -> {
                    if (getListener() != null) {
                        getListener().onClose();
                    }
                }, Throwable::printStackTrace));

        getCompositeDisposable().add(personsViewModel.getPersonsBySpecId(id)
                .flatMap(this::getConvertObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataLoaded));

        getCompositeDisposable().add(RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .flatMap(o -> personsViewModel.getPersonsBySpecIdWithUpdate(id))
                .flatMap(this::getConvertObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataRefreshed));

        getCompositeDisposable().add(personsViewModel.getSpecName(id)
                .subscribe(s -> {
                    labelTv.setText(s);
                }));
    }

    private Observable<List<PersonItem>> getConvertObservable(List<Person> people) {
        return Observable.fromIterable(people)
                .map(UiUtils::personToAdapterItem)
                .toList()
                .toObservable();
    }

    private void onDataLoaded(List<PersonItem> persons) {
        swipeRefreshLayout.setRefreshing(false);
        if (!persons.isEmpty()) {
            emptyTv.setVisibility(View.GONE);
            personsRv.setVisibility(View.VISIBLE);
            onPopulateRecycler(persons);
        } else {
            emptyTv.setVisibility(View.VISIBLE);
            personsRv.setVisibility(View.GONE);
        }
    }

    private void onDataRefreshed(List<PersonItem> persons) {
        swipeRefreshLayout.setRefreshing(false);
        if (!persons.isEmpty()) {
            emptyTv.setVisibility(View.GONE);
            personsRv.setVisibility(View.VISIBLE);
            onPopulateRecycler(persons);
        } else {
            Toast.makeText(getContext(), getString(R.string.loading_error), Toast.LENGTH_LONG).show();
        }
    }

    private void onPopulateRecycler(List<PersonItem> persons) {
        RecyclerAdapter adapter = new RecyclerAdapter();
        PersonsHolder personsHolder = new PersonsHolder();
        personsHolder.setListener(id -> {
            if (getListener() != null) {
                getListener().onPersonsBySpecFragmentPersonSelected(id);
            }
        });
        adapter.setHolders(personsHolder);
        adapter.setItems(persons);
        personsRv.setAdapter(adapter);
    }

    public interface Listener extends BaseFragmentListener {

        void onPersonsBySpecFragmentPersonSelected(int id);
    }
}
