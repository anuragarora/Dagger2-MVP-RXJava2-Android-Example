package com.anurag.flickr.Repository;

import java.util.List;

/**
 * Interface for preference operations for Flickr Recent Photos Application
 */
public interface FlickrPreferences {
    String PREF_PHOTOS_FILE = "token_pref_file";
    String PREF_RECENT_PHOTOS_LAST_RESPONSE = "last_response";

    void writePreference(String fileName, String key, String value);

    void writePreference(String fileName, String key, long value);

    void writePreference(String fileName, String key, float value);

    void writePreference(String fileName, String key, int value);

    void writePreference(String fileName, String key, boolean value);

    String readPreference(String fileName, String key, String defaultValue);

    long readPreference(String fileName, String key, long defaultValue);

    int readPreference(String fileName, String key, int defaultValue);

    float readPreference(String fileName, String key, float defaultValue);

    boolean readPreference(String fileName, String key, boolean defaultValue);

    boolean contains(String fileName, String name);

    void clearAllExceptSpecified(String fileName, List<String> prefsNotToBeDeleted);
}
