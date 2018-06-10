package com.anurag.flickr.module;

import android.content.Context;

import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.image.PicassoImageLoader;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageLoaderModule {
    private final int MAX_CACHE_SIZE = 100 * 1024 * 1024;

    private Picasso sPicasso;

    @Provides
    @Singleton
    public ImageLoader picassoImageLoader(Context applicationContext) {
        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(applicationContext)
                    .downloader(new OkHttp3Downloader(applicationContext, MAX_CACHE_SIZE))
                    .build();
        }

        return new PicassoImageLoader(sPicasso);
    }
}
