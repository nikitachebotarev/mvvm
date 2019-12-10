package ru.cnv.sample.data.provider.abs;

import java.util.List;

import io.reactivex.Observable;
import ru.cnv.sample.data.provider.entity.Person;

public interface PersonsProvider {

    Observable<List<Person>> getPersonsBySpecId(int id);

    Observable<List<Person>> getAllPersons();

    Observable<String> getSpecName(int id);
}
