package com.anurag.flickr.event.photo;

import com.anurag.flickr.model.GetRecentPhotosResponse;

/**
 * /**
 * GetRecentPhotos api success event class
 */

public class GetRecentPhotosSuccessResponseEvent {
    private GetRecentPhotosResponse mSuccessResponse;
    private boolean isNetworkResponse;

    public GetRecentPhotosSuccessResponseEvent(GetRecentPhotosResponse mSuccessResponse,
                                               boolean isNetworkResponse) {
        this.mSuccessResponse = mSuccessResponse;
        this.isNetworkResponse = isNetworkResponse;
    }

    public GetRecentPhotosResponse getResponse() {
        return mSuccessResponse;
    }

    public boolean isNetworkResponse() {
        return isNetworkResponse;
    }
}
