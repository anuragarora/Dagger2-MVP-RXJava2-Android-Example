package com.anurag.flickr.Repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;

/**
 * Wrapper for android Preferences
 */
public class AndroidFlickrPreferences implements FlickrPreferences {
    private final Context mContext;

    public AndroidFlickrPreferences(Context context) {
        mContext = context;
    }

    @Override
    public void writePreference(String fileName, String key, String value) {
        getSharedPreferences(fileName).edit().putString(key, value).apply();
    }

    @Override
    public void writePreference(String fileName, String key, long value) {
        getSharedPreferences(fileName).edit().putLong(key, value).apply();
    }

    @Override
    public void writePreference(String fileName, String key, float value) {
        getSharedPreferences(fileName).edit().putFloat(key, value).apply();
    }

    @Override
    public void writePreference(String fileName, String key, int value) {
        getSharedPreferences(fileName).edit().putInt(key, value).apply();
    }

    @Override
    public void writePreference(String fileName, String key, boolean value) {
        getSharedPreferences(fileName).edit().putBoolean(key, value).apply();
    }

    @Override
    public String readPreference(String fileName, String key, String defaultValue) {
        return getSharedPreferences(fileName).getString(key, defaultValue);
    }

    @Override
    public long readPreference(String fileName, String key, long defaultValue) {
        return getSharedPreferences(fileName).getLong(key, defaultValue);
    }

    @Override
    public int readPreference(String fileName, String key, int defaultValue) {
        return getSharedPreferences(fileName).getInt(key, defaultValue);
    }

    @Override
    public float readPreference(String fileName, String key, float defaultValue) {
        return getSharedPreferences(fileName).getFloat(key, defaultValue);
    }

    @Override
    public boolean readPreference(String fileName, String key, boolean defaultValue) {
        return getSharedPreferences(fileName).getBoolean(key, defaultValue);
    }

    private SharedPreferences getSharedPreferences(String fileName) {
        return mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean contains(String fileName, String name) {
        return getSharedPreferences(fileName).contains(name);
    }

    @Override
    public void clearAllExceptSpecified(String fileName, List<String> prefsNotToBeDeleted) {
        if (prefsNotToBeDeleted == null) {
            getSharedPreferences(fileName).edit().clear().apply();
        } else {
            SharedPreferences preferences = getSharedPreferences(fileName);
            Map<String, ?> keys = preferences.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                if (!prefsNotToBeDeleted.contains(entry.getKey())) {
                    preferences.edit().remove(entry.getKey()).apply();
                }
            }
        }
    }
}
