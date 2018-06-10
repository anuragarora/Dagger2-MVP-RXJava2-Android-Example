package com.anurag.flickr.module;

import com.anurag.flickr.network.RetrofitNetworkManager;
import com.anurag.flickr.ui.activity.BaseActivity;
import com.anurag.flickr.ui.activity.GetRecentPhotosActivity;
import com.anurag.flickr.ui.adapter.PhotoAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anurag on 08/06/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ConverterModule.class,
        EventBusModule.class, ImageLoaderModule.class, LoaderModule.class,
        NetworkManagerModule.class, RepositoryModule.class})
public interface FlickrComponent {
    void inject(BaseActivity activity);
    void inject(GetRecentPhotosActivity activity);
    void inject(PhotoAdapter adapter);
}
