package ru.cnv.sample.ui.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ru.cnv.sample.R;
import ru.cnv.sample.ui.adapter.base.RecyclerHolder;
import ru.cnv.sample.ui.entity.SpecItem;

public class SpecHolder extends RecyclerHolder<SpecItem> {

    @BindView(R.id.root_vg)
    ViewGroup rootVg;

    @BindView(R.id.picture_label_tv)
    TextView pictureLabelTv;

    @BindView(R.id.label_tv)
    TextView labelTv;

    @BindView(R.id.button_tv)
    TextView buttonTv;

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayout() {
        return R.layout.item_spec;
    }

    @Override
    public int getType() {
        return SpecItem.class.hashCode();
    }

    @Override
    public void bind(SpecItem item) {
        labelTv.setText(item.getName());
        pictureLabelTv.setText(item.getName().substring(0, 1));
        rootVg.setOnClickListener(view -> {
            if (listener != null) {
                listener.onSpecSelected(item.getId());
            }
        });
    }

    public interface Listener {

        void onSpecSelected(int id);
    }
}
