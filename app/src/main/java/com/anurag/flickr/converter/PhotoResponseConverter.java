package com.anurag.flickr.converter;

import android.content.res.Resources;

import com.anurag.flickr.R;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.model.Photo;
import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;
import com.anurag.flickr.model.server.ServerPhoto;
import com.anurag.flickr.util.Logger;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Converter to convert server Recent ServerPhoto model to client Recent ServerPhoto model
 * which is as per app requirements.
 *
 * TODO: Converter is not working as in Retrofit 1.9, Retrofit 2.0 beta3. Need to look into changes!
 */
public class PhotoResponseConverter extends Converter.Factory {
    private static final String TAG = PhotoResponseConverter.class.getSimpleName();
    private final Converter.Factory mOriginalConverter;
    private final Resources mResources;

    public PhotoResponseConverter(Converter.Factory originalConverter, Resources resources) {
        mOriginalConverter = originalConverter;
        mResources = resources;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type != GetRecentPhotosResponse.class) {
            Logger.i(TAG, "Type: " + type);
            return mOriginalConverter.responseBodyConverter(type, annotations, retrofit);
        } else {
            return new Converter<ResponseBody, GetRecentPhotosResponse>() {
                @Override
                public GetRecentPhotosResponse convert(ResponseBody value) throws IOException {
                    //JsonObject jsonObj = new JsonObject(value.string());
                    ServerGetRecentPhotosSuccessResponse response =
                            new Gson().fromJson(value.charStream(), ServerGetRecentPhotosSuccessResponse.class);

                    if (response == null || response.getStatus() == null) {
                        return null;
                    } else {
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

                        GetRecentPhotosResponse photoResponse = new GetRecentPhotosResponse.Builder()
                                .page(response.getPhotos().getPage())
                                .pages(response.getPhotos().getPages())
                                .perPage(response.getPhotos().getPerpage())
                                .total(response.getPhotos().getPerpage())
                                .photoList(photoList)
                                .build();

                        return photoResponse;
                    }
                }
            };
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return mOriginalConverter.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
