package com.anurag.flickr.module;

import android.content.Context;

import com.anurag.flickr.Repository.AndroidFlickrPreferences;
import com.anurag.flickr.Repository.FlickrPreferences;
import com.anurag.flickr.Repository.PreferenceRecentPhotosRepository;
import com.anurag.flickr.Repository.RecentPhotosRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Repository module to facilitate dependency injection.
 */
@Module
public class RepositoryModule {
    private FlickrPreferences androidFlickrPreferences(Context applicationContext) {
        return new AndroidFlickrPreferences(applicationContext);
    }

    @Provides
    @Singleton
    public RecentPhotosRepository recentPhotosRepository(Context applicationContext) {
        return new PreferenceRecentPhotosRepository(androidFlickrPreferences(applicationContext));
    }
}
