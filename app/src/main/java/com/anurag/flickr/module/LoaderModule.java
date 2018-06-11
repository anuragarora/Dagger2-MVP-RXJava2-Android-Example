package com.anurag.flickr.module;
import com.anurag.flickr.loader.GetPhotoLoader;
import com.anurag.flickr.loader.GetPhotoNetworkLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for fetcher/loader to facilitate dependency injection
 */
@Module
public class LoaderModule {

    @Provides
    @Singleton
    public GetPhotoLoader getPhotosFetcher(GetPhotoNetworkLoader photoNetworkLoader) {
        return photoNetworkLoader;
    }
}
