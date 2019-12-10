package ru.cnv.sample.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class PreferenceManager {

    public static final String KEY_UPDATE_INFO = "update_info";

    private SharedPreferences preferences;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences("default", Context.MODE_PRIVATE);
    }

    public void saveObject(Object object, String key) throws IOException {
        String string = new Gson().toJson(object);
        preferences.edit().putString(key, string).apply();
    }

    public  <T> T getObject(Class<T> clazz, String key) throws IOException {
        String string = preferences.getString(key, null);
        if (string != null) {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(string);
            return new Gson().fromJson(jsonObject, clazz);
        }
        return null;
    }
}
