package ru.cnv.sample.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import ru.cnv.App;
import ru.cnv.sample.data.api.RetrofitClientFactory;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;
import ru.cnv.sample.data.provider.PersonDetailsProviderImpl;
import ru.cnv.sample.data.provider.PersonsProviderImpl;
import ru.cnv.sample.data.provider.SpecsProviderImpl;
import ru.cnv.sample.data.provider.UpdateProviderImpl;
import ru.cnv.sample.data.provider.abs.PersonDetailsProvider;
import ru.cnv.sample.data.provider.abs.PersonsProvider;
import ru.cnv.sample.data.provider.abs.SpecsProvider;
import ru.cnv.sample.data.provider.abs.UpdateProvider;
import ru.cnv.sample.vm.PersonDetailViewModel;
import ru.cnv.sample.vm.PersonsViewModel;
import ru.cnv.sample.vm.SpecsViewModel;

@Module
public class AppModule {

    @Provides
    Application application() {
        return App.getInstance();
    }

    @Provides
    Context context(Application application) {
        return application;
    }

    @Provides
    PreferenceManager preferenceManager(Context context) {
        return new PreferenceManager(context);
    }

    @Provides
    RetrofitService retrofitService() {
        return RetrofitClientFactory.getService(RetrofitService.class, RetrofitService.BASE_URL);
    }

    @Provides
    Realm realm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    UpdateProvider updateProvider(PreferenceManager preferenceManager, RetrofitService retrofitService, Realm realm) {
        return new UpdateProviderImpl(retrofitService, preferenceManager, realm);
    }

    @Provides
    PersonsProvider personsProvider(PreferenceManager preferenceManager, RetrofitService retrofitService, Realm realm) {
        return new PersonsProviderImpl(retrofitService, preferenceManager, realm);
    }

    @Provides
    PersonDetailsProvider personDetailsProvider(PreferenceManager preferenceManager, RetrofitService retrofitService, Realm realm) {
        return new PersonDetailsProviderImpl(retrofitService, preferenceManager, realm);
    }

    @Provides
    SpecsProvider specsProvider(PreferenceManager preferenceManager, RetrofitService retrofitService, Realm realm) {
        return new SpecsProviderImpl(retrofitService, preferenceManager, realm);
    }
}
