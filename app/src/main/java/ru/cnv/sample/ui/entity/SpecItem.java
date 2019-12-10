package ru.cnv.sample.ui.entity;

import ru.cnv.sample.data.provider.entity.Spec;
import ru.cnv.sample.ui.adapter.base.RecyclerItem;

public class SpecItem extends Spec implements RecyclerItem {

    @Override
    public int getType() {
        return getClass().hashCode();
    }
}
