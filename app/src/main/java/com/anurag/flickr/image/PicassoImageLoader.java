package com.anurag.flickr.image;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Implementation of the ImageLoader interface via Picasso
 */
public class PicassoImageLoader implements ImageLoader {
    private final Picasso mPicasso;

    public PicassoImageLoader(Picasso picasso) {
        mPicasso = picasso;
    }

    @Override
    public void loadImage(String url, int placeholder, int errorPlaceholder,
                          boolean resize, ImageView imageView) {
        if (resize) {
            mPicasso.load(url)
                    .placeholder(placeholder)
                    .error(errorPlaceholder)
                    .resize(768, 1024)
                    .centerInside()
                    .into(imageView);
        } else {
            mPicasso.load(url)
                    .placeholder(placeholder)
                    .error(errorPlaceholder)
                    .into(imageView);
        }
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        mPicasso.load(url).centerCrop()
                .into(imageView);
    }
}
