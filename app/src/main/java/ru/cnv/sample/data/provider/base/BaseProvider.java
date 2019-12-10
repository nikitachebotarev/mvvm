package ru.cnv.sample.data.provider.base;

import io.realm.Realm;
import ru.cnv.sample.data.api.RetrofitService;
import ru.cnv.sample.data.pref.PreferenceManager;

public class BaseProvider {

    private RetrofitService retrofitService;
    private PreferenceManager preferenceManager;
    private Realm realm;

    public BaseProvider(RetrofitService retrofitService, PreferenceManager preferenceManager, Realm realm) {
        this.retrofitService = retrofitService;
        this.preferenceManager = preferenceManager;
        this.realm = realm;
    }

    public RetrofitService getRetrofitService() {
        return retrofitService;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public Realm getRealm() {
        return realm;
    }
}
