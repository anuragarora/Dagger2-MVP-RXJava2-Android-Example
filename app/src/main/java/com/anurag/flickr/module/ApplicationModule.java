package com.anurag.flickr.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application module facilitates access to app wide services and objects by any class.
 */
@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides @Singleton
    Context applicationContext() {
        return mApplication;
    }

    @Provides @Singleton
    public Resources resources() {
        return mApplication.getResources();
    }

    @Provides
    @Singleton
    LayoutInflater layoutInflater() {
        return (LayoutInflater) applicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Provides @Singleton
    LocalBroadcastManager getBroadcastManager() {
        return LocalBroadcastManager.getInstance(applicationContext());
    }
}
