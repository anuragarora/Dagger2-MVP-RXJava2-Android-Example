package com.anurag.flickr.module;


import android.content.res.Resources;

import com.anurag.flickr.converter.PhotoResponseConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Converter to help with request/response serialization/deserialization.
 */
@Module
public class ConverterModule {
    private final String GSON_DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";
    private final Gson gson = new GsonBuilder()
            .setDateFormat(GSON_DATE_FORMAT)
            .create();

    @Singleton
    @Provides @Named("gson")
    Converter.Factory gsonConverter() {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides @Named("photos")
    Converter.Factory recentPhotosConverter(Resources resources) {
        return new PhotoResponseConverter(gsonConverter(), resources);
    }
}
