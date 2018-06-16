package com.anurag.flickr.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anurag.flickr.Application;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Base activity for the application
 */
public class BaseActivity extends AppCompatActivity {
    @Inject EventBus mEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting
        Application.getApplicationComponent().inject(this);
    }
}
