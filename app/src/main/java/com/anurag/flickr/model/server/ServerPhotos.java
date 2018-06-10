package com.anurag.flickr.model.server;

import com.anurag.flickr.model.server.ServerPhoto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anurag on 17/05/18.
 */

public class ServerPhotos {
    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("perpage")
    private int mPerpage;

    @SerializedName("total")
    private int mTotal;

    @SerializedName("photo")
    private List<ServerPhoto> mServerPhotoList;

    public int getPage() {
        return mPage;
    }

    public int getPages() {
        return mPages;
    }

    public int getPerpage() {
        return mPerpage;
    }

    public int getTotal() {
        return mTotal;
    }

    public List<ServerPhoto> getPhotoList() {
        return mServerPhotoList;
    }

}
