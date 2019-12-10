package ru.cnv.sample.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.cnv.sample.R;
import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.ui.adapter.SpecHolder;
import ru.cnv.sample.ui.adapter.base.RecyclerAdapter;
import ru.cnv.sample.ui.entity.SpecItem;
import ru.cnv.sample.ui.fragment.base.BaseFragment;
import ru.cnv.sample.ui.fragment.base.BaseFragmentListener;
import ru.cnv.sample.ui.utils.UiUtils;
import ru.cnv.sample.vm.SpecsViewModel;

public class SpecsFragment extends BaseFragment<SpecsFragment.Listener> {

    private SpecsViewModel viewModel;

    @BindView(R.id.specs_rv)
    RecyclerView specsRv;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_tv)
    TextView emptyTv;

    @Override
    protected int getLayout() {
        return R.layout.fragment_specs;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SpecsViewModel.class);
    }

    @Override
    @WorkerThread
    protected void initViewModels() {
        if (!viewModel.isInitiated()) {
            viewModel.init();
        }
    }

    @Override
    protected void onViewModelsInitiated() {
        specsRv.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));

        getCompositeDisposable().add(viewModel.getSpecs()
                .flatMap(this::getConvertObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataLoaded));

        getCompositeDisposable().add(RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .flatMap(o -> viewModel.getSpecsWithUpdate())
                .flatMap(this::getConvertObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataRefreshed));
    }

    private void onDataLoaded(List<SpecItem> specs) {
        swipeRefreshLayout.setRefreshing(false);
        if (!specs.isEmpty()) {
            emptyTv.setVisibility(View.GONE);
            specsRv.setVisibility(View.VISIBLE);
            onPopulateRecycler(specs);
        } else {
            emptyTv.setVisibility(View.VISIBLE);
            specsRv.setVisibility(View.GONE);
        }
    }

    private void onDataRefreshed(List<SpecItem> specs) {
        swipeRefreshLayout.setRefreshing(false);
        if (!specs.isEmpty()) {
            emptyTv.setVisibility(View.GONE);
            specsRv.setVisibility(View.VISIBLE);
            onPopulateRecycler(specs);
        } else {
            Toast.makeText(getContext(), getString(R.string.loading_error), Toast.LENGTH_LONG).show();
        }
    }

    private void onPopulateRecycler(List<SpecItem> specs) {
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setItems(specs);
        SpecHolder specHolder = new SpecHolder();
        specHolder.setListener(id -> {
            if (getListener() != null) {
                getListener().onSpecsFragmentSpecSelected(id);
            }
        });
        recyclerAdapter.setHolders(specHolder);
        specsRv.setAdapter(recyclerAdapter);
    }

    private Observable<List<SpecItem>> getConvertObservable(List<Spec> specs) {
        return Observable.fromIterable(specs)
                .map(UiUtils::specToAdapterItem)
                .toList()
                .toObservable();
    }

    public interface Listener extends BaseFragmentListener {

        void onSpecsFragmentSpecSelected(int id);
    }
}
