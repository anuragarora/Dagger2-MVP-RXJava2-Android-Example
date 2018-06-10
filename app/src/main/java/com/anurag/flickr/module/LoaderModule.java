package com.anurag.flickr.module;


import android.content.res.Resources;

import com.anurag.flickr.Repository.RecentPhotosRepository;
import com.anurag.flickr.loader.GetPhotoLoader;
import com.anurag.flickr.loader.GetPhotoNetworkLoader;
import com.anurag.flickr.network.NetworkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Module for fetcher/loader to facilitate dependency injection
 */
@Module
public class LoaderModule {

    @Provides
    @Singleton
    public GetPhotoLoader getPhotosFetcher(NetworkManager networkManager, EventBus eventBus,
                                           Resources resources, RecentPhotosRepository recentPhotosRepository) {
        return new GetPhotoNetworkLoader(networkManager, eventBus, resources, recentPhotosRepository);
    }
}
