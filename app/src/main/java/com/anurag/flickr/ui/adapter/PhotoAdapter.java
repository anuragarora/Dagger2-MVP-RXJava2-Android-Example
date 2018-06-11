package com.anurag.flickr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anurag.flickr.R;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.model.Photo;
import com.anurag.flickr.ui.viewholder.RecentPhotoViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * PhotoAdapter for Flickr Recents List View
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecentPhotoViewHolder> {
    private static final String TAG = PhotoAdapter.class.getSimpleName();
    @Inject ImageLoader mImageLoader;

    private final List<Photo> mPhotoList = new ArrayList<>();

    @Inject
    public PhotoAdapter() {}

    public void addToList(List<Photo> list) {
        mPhotoList.addAll(list);
    }

    public void clearList() {
        mPhotoList.clear();
    }

    @Override
    public RecentPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_photo, parent, false);
        return new RecentPhotoViewHolder(view, mImageLoader);
    }

    @Override
    public void onBindViewHolder(RecentPhotoViewHolder holder, int position) {
        holder.bindViewHolder(mPhotoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
