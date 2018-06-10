package com.anurag.flickr.network;

import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Endpoints for the REST web services
 */
public interface FlickrApi {
    @GET("/services/rest")
    Call<ServerGetRecentPhotosSuccessResponse> getRecentPhotos(@Query("method") String method,
                                                               @Query("page") String page);
}
