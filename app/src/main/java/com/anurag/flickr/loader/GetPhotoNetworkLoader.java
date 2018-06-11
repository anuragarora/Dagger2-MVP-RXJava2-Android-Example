package com.anurag.flickr.loader;

import android.content.res.Resources;

import com.anurag.flickr.R;
import com.anurag.flickr.Repository.RecentPhotosRepository;
import com.anurag.flickr.event.photo.GetRecentPhotosFailureEvent;
import com.anurag.flickr.event.photo.GetRecentPhotosSuccessResponseEvent;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.model.Photo;
import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;
import com.anurag.flickr.model.server.ServerPhoto;
import com.anurag.flickr.network.NetworkManager;
import com.anurag.flickr.util.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Loader for GetPhotoNetworkLoader
 * TODO Need to wrap Callback to accept a Success and a failure response and facilitate testing.
 * TODO: Send a failure response with Failure object and message from server on failure response.
 */
public class GetPhotoNetworkLoader implements GetPhotoLoader, Callback<ServerGetRecentPhotosSuccessResponse> {
    private static final String STATUS_PASS = "ok";

    @Inject NetworkManager mNetworkManager;
    @Inject EventBus mEventBus;
    @Inject Resources mResources;
    @Inject RecentPhotosRepository mRecentPhotosRepository;

    @Inject
    public GetPhotoNetworkLoader() {
    }

    @Override
    public void getRecentPhotos(int page) {
        if (page == 0) {
            GetRecentPhotosResponse lastResponse = new Gson().fromJson(mRecentPhotosRepository
                    .getRecentPhotosLastResponse(), GetRecentPhotosResponse.class);
            if (lastResponse != null) {
                mEventBus.post(new GetRecentPhotosSuccessResponseEvent(lastResponse, false));
            }

            mNetworkManager.getRecentPhotos(1, this);
        } else {
            mNetworkManager.getRecentPhotos(page, this);
        }
    }

    @Override
    public void onResponse(Call<ServerGetRecentPhotosSuccessResponse> call, Response<ServerGetRecentPhotosSuccessResponse> response) {
        //noinspection ConstantConditions - getStatus() is never null
        if (response.isSuccessful() && response.body().getStatus().equals(STATUS_PASS)) {
            Logger.i(GetPhotoNetworkLoader.class.getSimpleName(), "Response successfully received");

            /*
             * Saving last response in repository so app can get instant data till
             * network call gets a new response
             */
            GetRecentPhotosResponse photosResponse = convertToClientResponse(response.body());
            mRecentPhotosRepository.saveRecentPhotosLastResponse(new Gson()
                    .toJson(photosResponse, GetRecentPhotosResponse.class));

            // Posting event to activity for display
            mEventBus.post(new GetRecentPhotosSuccessResponseEvent(photosResponse,
                    true));
        } else {
            onFailure(call, new Exception("Unable to parse response"));
        }
    }

    @Override
    public void onFailure(Call<ServerGetRecentPhotosSuccessResponse> call, Throwable t) {
        mEventBus.post(new GetRecentPhotosFailureEvent(t.getMessage()));
        Logger.i(GetPhotoNetworkLoader.class.getSimpleName(), "Failure Response: " + t.getMessage());
    }

    // TODO: This has to be done in Converter. Need to look into changes in Retrofit 2.x.
    private GetRecentPhotosResponse convertToClientResponse(ServerGetRecentPhotosSuccessResponse response) {
        List<Photo> photoList = new ArrayList<>();
        for (ServerPhoto serverPhoto : response.getPhotos().getPhotoList()) {
            String url = mResources
                    .getString(R.string.api_image_url,
                            serverPhoto.getFarm(),
                            serverPhoto.getServer(),
                            serverPhoto.getId(),
                            serverPhoto.getSecret());

            photoList.add(new Photo.Builder()
                    .title(serverPhoto.getTitle())
                    .url(url)
                    .build());
        }

        return new GetRecentPhotosResponse.Builder()
                .page(response.getPhotos().getPage())
                .pages(response.getPhotos().getPages())
                .perPage(response.getPhotos().getPerpage())
                .total(response.getPhotos().getTotal())
                .photoList(photoList)
                .build();
    }
}
