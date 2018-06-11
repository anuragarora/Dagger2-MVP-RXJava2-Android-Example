package com.anurag.flickr.network;

import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;

import io.reactivex.Single;
import retrofit2.Callback;

/**
 * Network Manager interface.
 */
public interface NetworkManager {
    Single<ServerGetRecentPhotosSuccessResponse> getRecentPhotos(int page);
}
