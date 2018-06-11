package com.anurag.flickr.module;


import android.content.Context;
import android.content.res.Resources;

import com.anurag.flickr.R;
import com.anurag.flickr.network.BasicAuthorizationRequestInterceptor;
import com.anurag.flickr.network.NetworkManager;
import com.anurag.flickr.network.RetrofitNetworkManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Converter;

/**
 * Network Manager Module
 */
@Module
public class NetworkManagerModule {
    private static final int TIMEOUT = 60;
    private static final String TAG = NetworkManagerModule.class.getSimpleName();

    @Provides
    @Singleton
    Cache okHttpCache(Context context) {
        int cacheSize = 10 * 10 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient okClient(Resources resources, Cache cache) {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(new BasicAuthorizationRequestInterceptor(resources))
                .build();
    }


    /*@Provides
    @Singleton
    NetworkManager retrofitNetworkManager(OkHttpClient okClient,
                                          Resources resources,
                                          @Named("gson") Converter.Factory gsonConverter,
                                          @Named("photos") Converter.Factory recentPhotosConverter) {
        return new RetrofitNetworkManager(okClient, resources.getString(R.string.base_url),
                gsonConverter, recentPhotosConverter);
    }*/

    @Provides
    @Singleton
    NetworkManager retrofitNetworkManager(RetrofitNetworkManager retrofitNetworkManager) {
        return retrofitNetworkManager;
    }
}
