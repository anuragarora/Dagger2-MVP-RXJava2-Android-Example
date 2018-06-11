package com.anurag.flickr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anurag.flickr.Application;
import com.anurag.flickr.R;
import com.anurag.flickr.event.global.NetworkStatusChangedEvent;
import com.anurag.flickr.event.photo.GetRecentPhotosFailureEvent;
import com.anurag.flickr.event.photo.GetRecentPhotosSuccessResponseEvent;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.loader.GetPhotoLoader;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.ui.adapter.PhotoAdapter;
import com.anurag.flickr.util.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by anurag on 09/06/18.
 */

public class PhotoListFragment extends Fragment {
    private static final String TAG = PhotoListFragment.class.getSimpleName();
    @Inject EventBus mEventBus;
    @Inject PhotoAdapter mAdapter;
    @Inject GetPhotoLoader mPhotoFetcher;
    Snackbar mSnackbar;

    @BindView(R.id.fragment_flickr_recents_container)
    public CoordinatorLayout mCoordinator;

    @BindView(R.id.fragment_flickr_list_view)
    public RecyclerView mListView;

    @BindView(R.id.fragment_flickr_recents_progress)
    public ProgressBar mLoadingIndicator;

    @Inject ImageLoader imageLoader;

    private View mFooterView;
    private int mCurrentPage = 0;
    private Unbinder mUnbinder;

    public static PhotoListFragment newInstance() {
        return new PhotoListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_photos, container, false);

        mUnbinder = ButterKnife.bind(this, view);
        mEventBus.register(this);

        mLoadingIndicator.setVisibility(View.VISIBLE);
        mSnackbar = Snackbar.make(mCoordinator, R.string.activity_network_unavailable_copy,
                Snackbar.LENGTH_INDEFINITE);

        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //mAdapter = new PhotoAdapter(imageLoader);

        mFooterView = inflater.inflate(R.layout.view_photo_list_footer, null, false);
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoFetcher.getRecentPhotos(++mCurrentPage);
            }
        });

        // Making get photos network call
        mPhotoFetcher.getRecentPhotos(mCurrentPage);

        return view;
    }

    public void onEvent(NetworkStatusChangedEvent event) {
        if (event.isNetworkEnabled()) {
            if (mSnackbar.isShown())
                mSnackbar.dismiss();

            // Perform additional action when network connection is back here
        } else {
            mSnackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Perform any additional action ?
                     Snack bar automatically dismisses itself onClick. */
                }
            }).show();
        }
    }

    /**
     * Invoked when GetRecentPhotos network call succeeds
     *
     * @param event Success Event object
     */
    public void onEvent(GetRecentPhotosSuccessResponseEvent event) {
        final GetRecentPhotosResponse response = event.getResponse();
        // Initializing footerView for loading more items onClick
        // Also check if response is same as last response which was persisted

        if (event.isNetworkResponse()) {
            mCurrentPage = response.getPage();
            /*if (mCurrentPage == 1) {
                mAdapter.clearList();
                binding.fragmentFlickrListView.addFooterView(mFooterView);
            }*/
            if (response.getPage() * response.getPerPage() >= response.getTotal()) {
                mFooterView.setVisibility(View.GONE);
            } else {
                mFooterView.setVisibility(View.VISIBLE);
            }
        }
        mAdapter.addToList(response.getPhotoList());
        mAdapter.notifyDataSetChanged();

        if (mListView.getAdapter() == null) {
            mListView.setAdapter(mAdapter);
            mLoadingIndicator.setVisibility(View.GONE);
        }
    }

    public void onEvent(GetRecentPhotosFailureEvent event) {
        Logger.i(TAG, "Inside failure event: " + event.getMessage());
        Snackbar.make(mCoordinator, event.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mEventBus.unregister(this);
    }
}
