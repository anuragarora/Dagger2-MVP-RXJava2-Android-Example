package com.anurag.flickr.network;

import android.content.res.Resources;

import com.anurag.flickr.R;
import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Implementation class of NetworkManager interface via RetrofitNetworkManager
 */
public class RetrofitNetworkManager implements NetworkManager {
    private static final String TAG = RetrofitNetworkManager.class.getSimpleName();
    private final String GET_RECENT_PHOTOS_QUERY_METHOD = "flickr.photos.getRecent";

    //@Inject Retrofit retrofit;

    @Inject OkHttpClient mOkClient;
    @Inject Resources mResources;
    @Inject @Named("gson") Converter.Factory mGsonConverter;
    @Inject @Named("photos") Converter.Factory mRecentPhotosConverter;

    /*public RetrofitNetworkManager(OkHttpClient okClient,
                                  String baseUrl,
                                  Converter.Factory gsonConverter,
                                  Converter.Factory recentPhotosConverter) {

        this.mOkClient = okClient;
        this.mBaseUrl = baseUrl;
        this.mGsonConverter = gsonConverter;
        this.mRecentPhotosConverter = recentPhotosConverter;
    }*/

    @Inject
    public RetrofitNetworkManager() {}

    /**
     * Creates a partially build retrofit object for FlickrApi service.
     *
     * @return Builder for retrofit with OpenWeatherMap base url and okHttpClient
     */
    private Retrofit.Builder getAdapter() {
        return new Retrofit.Builder()
                .baseUrl(mResources.getString(R.string.base_url))
                .client(mOkClient);
    }

    @Override
    public Single<ServerGetRecentPhotosSuccessResponse> getRecentPhotos(int page) {
        return getAdapter()
                .addConverterFactory(mGsonConverter)
                .build()
                .create(FlickrApi.class)
                .getRecentPhotos(GET_RECENT_PHOTOS_QUERY_METHOD, String.valueOf(page));
    }
}
