package ru.cnv.sample.data.provider.abs;

import java.util.List;

import io.reactivex.Observable;
import ru.cnv.sample.data.provider.entity.Spec;

public interface SpecsProvider {

    Observable<List<Spec>> getSpecs();
}
