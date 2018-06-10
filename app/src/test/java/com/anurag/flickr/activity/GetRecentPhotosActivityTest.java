package com.anurag.flickr.activity;

import android.view.WindowManager;

import com.anurag.flickr.BuildConfig;
import com.anurag.flickr.event.global.NetworkStatusChangedEvent;
import com.anurag.flickr.event.photo.GetRecentPhotosSuccessResponseEvent;
import com.anurag.flickr.image.ImageLoader;
import com.anurag.flickr.loader.GetPhotoLoader;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.model.Photo;
import com.anurag.flickr.ui.activity.GetRecentPhotosActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for GetRecentPhotosActivity
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetRecentPhotosActivityTest {
    private static final String TAG = GetRecentPhotosActivityTest.class.getSimpleName();
    private final GetRecentPhotosSuccessResponseEvent testSuccessFromNetworkResponseEvent
            = new GetRecentPhotosSuccessResponseEvent(new GetRecentPhotosResponse
            .Builder().page(1).perPage(100).total(1000)
            .photoList(new ArrayList<Photo>(10)).build(), true);

    private ActivityController<GetRecentPhotosActivity> mActivityController;
    private GetRecentPhotosActivity mActivity;

    @Mock
    GetPhotoLoader mMockGetPhotoLoader;

    @Mock
    ImageLoader mMockImageLoader;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mActivity = new GetRecentPhotosActivity();

        // To help drive activity lifecycle
        mActivityController = ActivityController.of(mActivity);
    }

    @Test
    public void testOnCreateMakesCallToGetPhotoServiceOnce() {
        // Given


        // When
        mActivity = mActivityController.create().get();

        // Then
        verify(mMockGetPhotoLoader, times(1)).getRecentPhotos(anyInt());
    }

    @Test
    public void testFirstCallToGetRecentPhotoServiceHasPage0() {
        // When
        mActivity = mActivityController.create().get();

        // Then
        verify(mMockGetPhotoLoader).getRecentPhotos(eq(0));
    }

    @Test
    public void testDisabledNetworkShowsSnackbar() {
        // Given
        mActivity = mActivityController.create().start().resume().visible().get();

        // When
        mActivity.onEvent(new NetworkStatusChangedEvent(false));

        // Then
        assertTrue(mActivity.mSnackbar.isShown());
    }

    @Test
    public void testEnabledNetworkDoesntShowSnackbar() {
        // Given
        mActivity = mActivityController.create().start().resume().visible().get();

        // When
        mActivity.onEvent(new NetworkStatusChangedEvent(true));

        // Then
        assertFalse(mActivity.mSnackbar.isShown());
    }

    @Test
    public void testListViewHasAFooterViewOnGetPhotosSuccessResponseEventFromNetwork() {
        // Given
        mActivity = mActivityController.create().start().resume().visible().get();

        // When
        mActivity.onEvent(testSuccessFromNetworkResponseEvent);

        // Then
        assertEquals(mActivity.mListView.getFooterViewsCount(), 1);
    }

    @Test
    public void testTitleIsCorrect() throws Exception {
        // When
        mActivityController.create();

        // Then
        assertThat(mActivity.getTitle().toString(), equalTo("Flickr Recents"));
    }

    @Test
    public void testFullScreenFlagIsSet() throws Exception {
        // When
        mActivityController.create();

        // Then
        assertThat(mActivity.getWindow().getAttributes().flags
                & WindowManager.LayoutParams.FLAG_FULLSCREEN, not(0));
    }
}
