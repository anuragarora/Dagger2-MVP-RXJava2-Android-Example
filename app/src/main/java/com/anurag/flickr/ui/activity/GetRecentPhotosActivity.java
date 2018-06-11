package com.anurag.flickr.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.anurag.flickr.R;
import com.anurag.flickr.ui.fragment.PhotoListFragment;

/**
 * Activity showing a list view of Recent Flickr Pics (upto a 1000 pics) from API
 */
public class GetRecentPhotosActivity extends BaseActivity {
    private static final String TAG = GetRecentPhotosActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Making app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Application.getApplicationComponent().inject(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, PhotoListFragment.newInstance(), "PhotoList")
                .commit();
    }

    /**
     * Invoked when GetRecentPhotos network call fails
     *
     * @param event Failure Event object
     */

}
