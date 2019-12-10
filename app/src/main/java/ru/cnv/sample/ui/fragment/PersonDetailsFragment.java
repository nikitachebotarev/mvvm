package ru.cnv.sample.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.cnv.App;
import ru.cnv.sample.R;
import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.data.provider.entity.PersonWithSpecs;
import ru.cnv.sample.ui.fragment.base.BaseFragment;
import ru.cnv.sample.ui.fragment.base.BaseFragmentListener;
import ru.cnv.sample.ui.utils.UiUtils;
import ru.cnv.sample.vm.PersonDetailViewModel;

public class PersonDetailsFragment extends BaseFragment<BaseFragmentListener> {

    public static final String ARG_ID = "id";

    PersonDetailViewModel viewModel;

    @BindView(R.id.title_tv)
    TextView titleTv;

    @BindView(R.id.subtitle_tv)
    TextView subtitleTv;

    @BindView(R.id.back_iv)
    ImageView backIv;

    @BindView(R.id.menu_iv)
    ImageView menuIv;

    @BindView(R.id.button_tv)
    TextView buttonTv;

    @BindView(R.id.second_button_iv)
    ImageView secondButtonTv;

    @BindView(R.id.avatar_iv)
    ImageView avatarIv;

    @BindView(R.id.avatar_label_tv)
    TextView avatarLabelTv;

    @BindView(R.id.picture_iv)
    ImageView pictureIv;

    @Override
    protected int getLayout() {
        return R.layout.fragment_person_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PersonDetailViewModel.class);
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
        if (getArguments() == null) {
            return;
        }

        int id = getArguments().getInt(ARG_ID);

        getCompositeDisposable().add(RxView.clicks(backIv)
                .throttleFirst(UiUtils.CLICKS_THROTTLE_MILLIS, TimeUnit.MILLISECONDS)
                .delay(UiUtils.CLICKS_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (getListener() != null) {
                        getListener().onClose();
                    }
                }));

        getCompositeDisposable().add(RxView.clicks(menuIv)
                .mergeWith(RxView.clicks(buttonTv))
                .mergeWith(RxView.clicks(secondButtonTv))
                .subscribe(o -> {
                    Toast.makeText(getContext(), getString(R.string.toast_stub), Toast.LENGTH_SHORT).show();
                }));

        getCompositeDisposable().add(viewModel.getPerson(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataLoaded));
    }

    private void onDataLoaded(PersonWithSpecs person) throws Exception {
        if (person.getPerson() != null) {
            titleTv.setText(String.format("%1$s %2$s",
                    person.getPerson().getFirstName(),
                    person.getPerson().getLastName()));

            subtitleTv.setText(getString(R.string.person_spec_and_age,
                    person.getSpecs().get(0).getName(),
                    UiUtils.toAge(person.getPerson().getBirthday())));

            if (!TextUtils.isEmpty(person.getPerson().getAvatar())) {
                Glide.with(getContext()).load(Uri.parse(person.getPerson().getAvatar())).into(avatarIv);
            } else {
                avatarIv.setImageResource(R.color.colorAccent);
                avatarLabelTv.setText(String.format("%1$s%2$s",
                        person.getPerson().getFirstName().substring(0, 1),
                        person.getPerson().getLastName().substring(0, 1)));
            }
        }
    }
}
