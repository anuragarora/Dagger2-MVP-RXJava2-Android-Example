package com.anurag.flickr.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anurag.flickr.Application;
import com.anurag.flickr.event.global.NetworkStatusChangedEvent;
import com.anurag.flickr.module.FlickrComponent;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Base activity for the application
 */
public class BaseActivity extends AppCompatActivity {
    private boolean mFirstDisconnect;
    private boolean mFirstConnect;
    @Inject EventBus mEventBus;

    /*public BaseActivity() {
        this(EventBusModule.eventBus());
    }*/

    /*public BaseActivity(EventBus eventBus) {
        mEventBus = eventBus;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting
        ((Application) getApplication()).getFlickrComponent().inject(this);
        mEventBus.registerSticky(this);

        // Network connectivity listener
        mFirstDisconnect = true;
        mFirstConnect = true;
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
        }

        mEventBus.unregister(this);
        super.onDestroy();
    }

    /**
     * I have created mFirstConnect and mFirstDisconnect booleans to protect the app code when the system
     * broadcasts send these events multiple times for a single going online/offline incident.
     * <p>
     * CAUTION: A connect/disconnect event will always be sent to the activity the first time this
     * receiver is registered (onCreate currently)
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                if (mFirstConnect) {
                    mEventBus.post(new NetworkStatusChangedEvent(true));
                    mFirstDisconnect = true;
                    mFirstConnect = false;
                }
            } else {
                if (mFirstDisconnect) {
                    mEventBus.post(new NetworkStatusChangedEvent(false));
                    mFirstDisconnect = false;
                    mFirstConnect = true;
                }
            }
        }
    };
}
