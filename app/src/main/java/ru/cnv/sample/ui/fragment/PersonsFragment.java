package ru.cnv.sample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.cnv.sample.R;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.ui.adapter.PersonsHolder;
import ru.cnv.sample.ui.adapter.base.RecyclerAdapter;
import ru.cnv.sample.ui.entity.PersonItem;
import ru.cnv.sample.ui.fragment.base.BaseFragment;
import ru.cnv.sample.ui.fragment.base.BaseFragmentListener;
import ru.cnv.sample.ui.utils.UiUtils;
import ru.cnv.sample.vm.PersonsViewModel;

public class PersonsFragment extends BaseFragment<PersonsFragment.Listener> {

    private PersonsViewModel personsViewModel;

    @BindView(R.id.persons_rv)
    RecyclerView personsRv;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_tv)
    TextView emptyTv;

    @Override
    protected int getLayout() {
        return R.layout.fragment_persones;
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
        getCompositeDisposable().add(personsViewModel.getAllPersons()
                .flatMap(this::getConverterObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataLoaded));

        getCompositeDisposable().add(RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .flatMap(o -> personsViewModel.getAllPersonsWithUpdate())
                .flatMap(this::getConverterObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataRefreshed));
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
                getListener().onPersonsFragmentPersonSelected(id);
            }
        });
        adapter.setHolders(personsHolder);
        adapter.setItems(persons);
        personsRv.setAdapter(adapter);
    }

    private Observable<List<PersonItem>> getConverterObservable(List<Person> items) {
        return Observable.fromIterable(items)
                .map(UiUtils::personToAdapterItem)
                .toList()
                .toObservable();
    }

    public interface Listener extends BaseFragmentListener {

        void onPersonsFragmentPersonSelected(int id);
    }
}