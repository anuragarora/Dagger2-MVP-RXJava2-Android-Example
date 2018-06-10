package com.anurag.flickr.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anurag.flickr.Application;
import com.anurag.flickr.R;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.model.Photo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * PhotoAdapter for Flickr Recents List View
 */

public class PhotoAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflator;
    @Inject ImageLoader mImageLoader;
    //@Inject LayoutInflater mLayoutInflator;

    private final List<Photo> mPhotoList = new ArrayList<>();

    public PhotoAdapter(Context context) {
        mLayoutInflator = LayoutInflater.from(context);
        ((Application)context.getApplicationContext()).getFlickrComponent().inject(this);
    }

    public void addToList(List<Photo> list) {
        mPhotoList.addAll(list);
    }

    public void clearList() {
        mPhotoList.clear();
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Photo photo = ((Photo) getItem(position));
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflator
                    .inflate(R.layout.view_photo, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Setting image
        mImageLoader.loadImage(photo.getUrl(),
                R.drawable.photo_placeholder,
                R.drawable.photo_placeholder,
                true, holder.mImage);

        // Setting Title
        holder.mTitle.setText(photo.getTitle());

        return view;
    }

    class ViewHolder {
        @BindView(R.id.view_photo_image_view)
        ImageView mImage;
        @BindView(R.id.view_photo_title)
        TextView mTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
