package com.anurag.flickr;

import com.anurag.flickr.module.ApplicationModule;
import com.anurag.flickr.module.ConverterModule;
import com.anurag.flickr.module.DaggerFlickrComponent;
import com.anurag.flickr.module.EventBusModule;
import com.anurag.flickr.module.FlickrComponent;
import com.anurag.flickr.module.ImageLoaderModule;
import com.anurag.flickr.module.LoaderModule;
import com.anurag.flickr.module.NetworkManagerModule;
import com.anurag.flickr.module.RepositoryModule;

/**
 * Class extends application to do application level stuff.
 */
public class Application extends android.app.Application {
    /*private static Application mAppInstance;

    public static Application getInstance() {
        return mAppInstance;
    }*/

    private static FlickrComponent mFlickrComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //mAppInstance = this;
        mFlickrComponent = DaggerFlickrComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .converterModule(new ConverterModule())
                .eventBusModule(new EventBusModule())
                .imageLoaderModule(new ImageLoaderModule())
                .loaderModule(new LoaderModule())
                .networkManagerModule(new NetworkManagerModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public static FlickrComponent getApplicationComponent() {
        return mFlickrComponent;
    }
}
