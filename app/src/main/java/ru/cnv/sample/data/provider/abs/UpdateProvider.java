package ru.cnv.sample.data.provider.abs;

import io.reactivex.Observable;

public interface UpdateProvider {

    Observable<Object> update();
}
