package com.anurag.flickr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
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
import com.anurag.flickr.Repository.RecentPhotosRepository;
import com.anurag.flickr.event.global.NetworkStatusChangedEvent;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.loader.GetPhotoLoader;
import com.anurag.flickr.loader.GetPhotoNetworkLoader;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.model.Photo;
import com.anurag.flickr.model.server.ServerGetRecentPhotosSuccessResponse;
import com.anurag.flickr.model.server.ServerPhoto;
import com.anurag.flickr.network.NetworkManager;
import com.anurag.flickr.ui.adapter.PhotoAdapter;
import com.anurag.flickr.util.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anurag on 09/06/18.
 */

public class PhotoListFragment extends Fragment {
    private static final String TAG = PhotoListFragment.class.getSimpleName();
    private static final String STATUS_PASS = "ok";
    @Inject
    EventBus mEventBus;
    @Inject
    PhotoAdapter mAdapter;
    @Inject
    GetPhotoLoader mPhotoFetcher;
    @Inject
    NetworkManager mNetworkManager;
    @Inject
    RecentPhotosRepository mRecentPhotosRepository;

    @VisibleForTesting
    Snackbar mSnackbar;

    @BindView(R.id.fragment_flickr_recents_container)
    public CoordinatorLayout mCoordinator;

    @BindView(R.id.fragment_flickr_list_view)
    public RecyclerView mListView;

    @BindView(R.id.fragment_flickr_recents_progress)
    public ProgressBar mLoadingIndicator;

    @Inject
    ImageLoader imageLoader;

    private View mFooterView;
    private int mCurrentPage = 0;
    private Unbinder mUnbinder;

    private CompositeDisposable mDisposable;

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
        mFooterView.setOnClickListener(v -> mPhotoFetcher.getRecentPhotos(++mCurrentPage));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDisposable = new CompositeDisposable();

        if (mCurrentPage == 0 && mRecentPhotosRepository.getRecentPhotosLastResponse() != null) {
            saveAndPostToAdapter(new Gson()
                    .fromJson(mRecentPhotosRepository
                    .getRecentPhotosLastResponse(), GetRecentPhotosResponse.class), false);
        } else {
            // Making get photos network call
            mDisposable.add(mNetworkManager.getRecentPhotos(mCurrentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFailure));
        }
    }

    private void onSuccess(ServerGetRecentPhotosSuccessResponse response) {
        if (response.getStatus().equals(STATUS_PASS)) {
            Logger.i(GetPhotoNetworkLoader.class.getSimpleName(), "Response successfully received");
            saveAndPostToAdapter(convertToClientResponse(response), true);
        } else {
            onFailure(new Exception("Unable to parse response"));
        }
    }

    private void onFailure(Throwable throwable) {
        Logger.i(TAG, "Inside failure event: " + throwable.getMessage());
        Snackbar.make(mCoordinator, throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mEventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    private void saveAndPostToAdapter(GetRecentPhotosResponse response,
                                      boolean fromNetwork) {
        /**
         * Saving last response in repository so app can get instant data till
         * network call gets a new response
         */
        mRecentPhotosRepository.saveRecentPhotosLastResponse(new Gson()
                .toJson(response, GetRecentPhotosResponse.class));

        // Initializing footerView for loading more items onClick
        // Also check if response is same as last response which was persisted
        if (fromNetwork) {
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

    @NonNull
    private GetRecentPhotosResponse convertToClientResponse(ServerGetRecentPhotosSuccessResponse response) {
        List<Photo> photoList = new ArrayList<>();
        for (ServerPhoto serverPhoto : response.getPhotos().getPhotoList()) {
            String url = getActivity().getResources()
                    .getString(R.string.api_image_url,
                            serverPhoto.getFarm(),
                            serverPhoto.getServer(),
                            serverPhoto.getId(),
                            serverPhoto.getSecret());

            photoList.add(new Photo.Builder()
                    .title(serverPhoto.getTitle())
                    .url(url)
                    .build());
        }

        return new GetRecentPhotosResponse.Builder()
                .page(response.getPhotos().getPage())
                .pages(response.getPhotos().getPages())
                .perPage(response.getPhotos().getPerpage())
                .total(response.getPhotos().getTotal())
                .photoList(photoList)
                .build();
    }
}
