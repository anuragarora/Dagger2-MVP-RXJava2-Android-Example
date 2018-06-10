package com.anurag.flickr.model.server;

import com.google.gson.annotations.SerializedName;

/**
 * Failure response model received from server
 * TODO: To be used after incorporating converter correctly.
 */

public class ServerGetRecentPhotosFailureResponse {
    @SerializedName("stat")
    private String mStat;

    @SerializedName("code")
    private int mCode;

    @SerializedName("message")
    private String mMessage;

    public String getmStat() {
        return mStat;
    }

    public int getmCode() {
        return mCode;
    }

    public String getmMessage() {
        return mMessage;
    }
}
