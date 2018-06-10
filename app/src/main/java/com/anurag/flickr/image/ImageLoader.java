package com.anurag.flickr.image;

import android.widget.ImageView;

public interface ImageLoader {
    void loadImage(String url, int placeholder, int errorPlaceholder, boolean resize, ImageView imageView);

    void loadImage(String url, ImageView imageView);
}
