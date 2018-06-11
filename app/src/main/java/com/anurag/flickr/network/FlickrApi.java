package com.anurag.flickr.network;

import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Endpoints for the REST web services
 */
public interface FlickrApi {
    @GET("/services/rest")
    Single<ServerGetRecentPhotosSuccessResponse> getRecentPhotos(@Query("method") String method,
                                                                 @Query("page") String page);
}
