package ru.cnv;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.cnv.sample.di.AppComponent;
import ru.cnv.sample.di.DaggerAppComponent;

public class App extends Application {

    private static App instance;

    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().build();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }
}
