package com.anurag.flickr.network;

import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;

import retrofit2.Callback;

/**
 * Network Manager interface.
 */
public interface NetworkManager {
    void getRecentPhotos(int page, Callback<ServerGetRecentPhotosSuccessResponse> callback);
}
