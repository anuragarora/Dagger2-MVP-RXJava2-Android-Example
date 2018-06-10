package com.anurag.flickr.model;

import java.util.List;

/**
 * GetRecentPhotos client response which is passed to the presentation layer.
 */
public class GetRecentPhotosResponse {
    private final int mPage;
    private final int mPages;
    private final int mPerPage;
    private final int mTotal;
    private final List<Photo> mPhotoList;

    private GetRecentPhotosResponse(Builder builder) {
        mPage = builder.mPage;
        mPages = builder.mPages;
        mPerPage = builder.mPerPage;
        mTotal = builder.mTotal;
        mPhotoList = builder.mPhotoList;
    }

    public static final class Builder {
        private int mPage;
        private int mPages;
        private int mPerPage;
        private int mTotal;
        private List<Photo> mPhotoList;

        public Builder() {
        }

        public Builder page(int val) {
            mPage = val;
            return this;
        }

        public Builder pages(int val) {
            mPages = val;
            return this;
        }

        public Builder perPage(int val) {
            mPerPage = val;
            return this;
        }

        public Builder total(int val) {
            mTotal = val;
            return this;
        }

        public Builder photoList(List<Photo> val) {
            mPhotoList = val;
            return this;
        }

        public GetRecentPhotosResponse build() {
            return new GetRecentPhotosResponse(this);
        }
    }

    public int getPage() {
        return mPage;
    }

    public int getPages() {
        return mPages;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public int getTotal() {
        return mTotal;
    }

    public List<Photo> getPhotoList() {
        return mPhotoList;
    }
}
