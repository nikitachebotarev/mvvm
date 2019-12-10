package ru.cnv.sample.ui.entity;

import ru.cnv.sample.data.provider.entity.Person;
import ru.cnv.sample.ui.adapter.base.RecyclerItem;

public class PersonItem extends Person implements RecyclerItem {

    @Override
    public int getType() {
        return getClass().hashCode();
    }
}
