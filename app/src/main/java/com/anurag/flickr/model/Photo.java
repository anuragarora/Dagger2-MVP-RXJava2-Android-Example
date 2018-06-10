package com.anurag.flickr.model;

/**
 * Client class for Photo object
 */

public class Photo {
    private final String mUrl;
    private final String mTitle;

    private Photo(Builder builder) {
        mUrl = builder.mUrl;
        mTitle = builder.mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }


    public static final class Builder {
        private String mUrl;
        private String mTitle;

        public Builder() {
        }

        public Builder url(String val) {
            mUrl = val;
            return this;
        }

        public Builder title(String val) {
            mTitle = val;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }
}
