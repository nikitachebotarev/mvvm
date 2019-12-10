package ru.cnv.sample.di;

import dagger.Component;
import ru.cnv.sample.ui.fragment.PersonDetailsFragment;
import ru.cnv.sample.ui.fragment.PersonsBySpecFragment;
import ru.cnv.sample.ui.fragment.PersonsFragment;
import ru.cnv.sample.ui.fragment.SpecsFragment;
import ru.cnv.sample.vm.PersonDetailViewModel;
import ru.cnv.sample.vm.PersonsViewModel;
import ru.cnv.sample.vm.SpecsViewModel;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(PersonsFragment personsFragment);

    void inject(PersonDetailsFragment personDetailsFragment);

    void inject(SpecsFragment specsFragment);

    void inject(PersonsBySpecFragment personsBySpecFragment);

    void inject(SpecsViewModel specsViewModel);

    void inject(PersonsViewModel personsViewModel);

    void inject(PersonDetailViewModel personDetailViewModel);
}
