package com.anurag.flickr.model.server;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Object received when getRecentPhotos call succeeds
 */

public class ServerGetRecentPhotosSuccessResponse {
    @SerializedName("photos")
    private ServerPhotos mServerPhotos;

    @SerializedName("stat")
    private String mStat;

    public ServerPhotos getPhotos() {
        return mServerPhotos;
    }

    public String getStatus() {
        return mStat;
    }
}
