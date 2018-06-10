package com.anurag.flickr.network;

import android.content.res.Resources;

import com.anurag.flickr.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp3 interceptor to add params common to every call for flickr api for my app!
 */
public class BasicAuthorizationRequestInterceptor implements Interceptor {
    private static final String PARAM_AUTHORIZATION = "api_key";
    private static final String PARAM_RESPONSE_FORMAT = "format";
    private static final String PARAM_JSON_CALLBACK = "nojsoncallback";

    private final Resources mResources;

    public BasicAuthorizationRequestInterceptor(Resources resources) {
        mResources = resources;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(PARAM_AUTHORIZATION, mResources.getString(R.string.api_key))
                .addQueryParameter(PARAM_RESPONSE_FORMAT, mResources.getString(R.string.api_response_format))
                .addQueryParameter(PARAM_JSON_CALLBACK,
                        Boolean.valueOf(mResources.getString(R.string.api_no_json_callback)) ? "1" : "0")
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}