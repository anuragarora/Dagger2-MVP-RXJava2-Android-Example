package com.anurag.flickr.Repository;

import static com.anurag.flickr.Repository.FlickrPreferences.PREF_PHOTOS_FILE;
import static com.anurag.flickr.Repository.FlickrPreferences.PREF_RECENT_PHOTOS_LAST_RESPONSE;

/**
 * Repository for tutorial related operations
 */
public class PreferenceRecentPhotosRepository implements RecentPhotosRepository {
    private final FlickrPreferences mPreferences;

    public PreferenceRecentPhotosRepository(FlickrPreferences pampersPreferences) {
        mPreferences = pampersPreferences;
    }

    @Override
    public String getRecentPhotosLastResponse() {
        return mPreferences.readPreference(PREF_PHOTOS_FILE, PREF_RECENT_PHOTOS_LAST_RESPONSE, null);
    }

    @Override
    public void saveRecentPhotosLastResponse(String value) {
        mPreferences.writePreference(PREF_PHOTOS_FILE, PREF_RECENT_PHOTOS_LAST_RESPONSE, value);
    }

    @Override
    public void clearAll() {
        mPreferences.clearAllExceptSpecified(PREF_PHOTOS_FILE, null);
    }
}
