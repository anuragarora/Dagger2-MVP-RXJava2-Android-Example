package com.anurag.flickr.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anurag.flickr.R;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.model.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anurag on 10/06/18.
 */

public class RecentPhotoViewHolder extends RecyclerView.ViewHolder {
    ImageLoader mImageLoader;

    @BindView(R.id.view_photo_image_view)
    ImageView mImage;

    @BindView(R.id.view_photo_title)
    TextView mTitle;

    public RecentPhotoViewHolder(View view, ImageLoader imageLoader) {
        super(view);
        ButterKnife.bind(this, view);
        mImageLoader = imageLoader;
    }

    public void bindViewHolder(Photo photo) {
        // Setting image
        mImageLoader.loadImage(photo.getUrl(),
                R.drawable.photo_placeholder,
                R.drawable.photo_placeholder,
                true, this.mImage);

        // Setting Title
        this.mTitle.setText(photo.getTitle());
    }
}