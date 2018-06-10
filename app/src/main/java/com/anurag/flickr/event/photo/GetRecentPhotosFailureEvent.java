package com.anurag.flickr.event.photo;

/**
 * GetRecentPhotos api failure event class
 */

public class GetRecentPhotosFailureEvent {
    private final String mMessage;

    public GetRecentPhotosFailureEvent(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
