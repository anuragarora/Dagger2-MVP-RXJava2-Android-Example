package com.anurag.flickr.model.server;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anurag on 17/05/18.
 */

public class ServerPhoto {
    @SerializedName("id")
    private String mId;

    @SerializedName("owner")
    private String mOwner;

    @SerializedName("secret")
    private String mSecret;

    @SerializedName("server")
    private String mServer;

    @SerializedName("farm")
    private String mFarm;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

    public String getId() {
        return mId;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getSecret() {
        return mSecret;
    }

    public String getServer() {
        return mServer;
    }

    public String getFarm() {
        return mFarm;
    }

    public String getTitle() {
        return mTitle;
    }

    public int isPublic() {
        return isPublic;
    }

    public int isFriend() {
        return isFriend;
    }

    public int isFamily() {
        return isFamily;
    }
}
