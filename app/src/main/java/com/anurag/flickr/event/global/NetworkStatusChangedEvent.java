package com.anurag.flickr.event.global;

/**
 * NetworkStatusChange(connected, disconnected) event class
 */
public class NetworkStatusChangedEvent {
    private boolean mNetworkEnabled;

    public NetworkStatusChangedEvent(boolean networkEnabled) {
        mNetworkEnabled = networkEnabled;
    }

    public boolean isNetworkEnabled() {
        return mNetworkEnabled;
    }
}
