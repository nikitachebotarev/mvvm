package ru.cnv.sample.data.provider.abs;

import io.reactivex.Observable;
import ru.cnv.sample.data.provider.entity.PersonWithSpecs;

public interface PersonDetailsProvider {

    Observable<PersonWithSpecs> getPersonWithSpecsById(int id);
}
