package ru.cnv.sample.ui.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import ru.cnv.sample.R;
import ru.cnv.sample.ui.adapter.base.RecyclerHolder;
import ru.cnv.sample.ui.entity.PersonItem;
import ru.cnv.sample.ui.utils.UiUtils;

public class PersonsHolder extends RecyclerHolder<PersonItem> {

    @BindView(R.id.picture_iv)
    ImageView pictureIv;

    @BindView(R.id.title_tv)
    TextView titleTv;

    @BindView(R.id.subtitle_tv)
    TextView subtitleTv;

    @BindView(R.id.root_vg)
    ViewGroup rootVg;

    @BindView(R.id.picture_label_tv)
    TextView pictureLabelTv;

    @BindView(R.id.right_subtitle_tv)
    TextView rightSubtitleTv;

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayout() {
        return R.layout.item_person;
    }

    @Override
    public int getType() {
        return PersonItem.class.hashCode();
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("")
    public void bind(PersonItem item) {
        titleTv.setText(String.format("%1$s %2$s", item.getFirstName(), item.getLastName()));
        if (item.getBirthday() != 0) {
            subtitleTv.setText(UiUtils.unixTimeToFormattedTime(item.getBirthday()));
        }
        if (item.getAvatar() != null) {
            Glide.with(pictureIv.getContext()).load(Uri.parse(item.getAvatar())).into(pictureIv);
        } else {
            pictureLabelTv.setText(String.format("%1$s%2$s", item.getFirstName().substring(0, 1),
                    item.getLastName().substring(0, 1)));
        }
        try {
            rightSubtitleTv.setText(rightSubtitleTv.getContext().getString(R.string.person_age,
                    UiUtils.toAge(item.getBirthday())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        RxView.clicks(rootVg)
                .throttleFirst(UiUtils.CLICKS_THROTTLE_MILLIS, TimeUnit.MILLISECONDS)
                .delay(UiUtils.CLICKS_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (listener != null) {
                        listener.onPersonSelected(item.getId());
                    }
                }, Throwable::printStackTrace);
    }

    public interface Listener {

        void onPersonSelected(int id);
    }
}