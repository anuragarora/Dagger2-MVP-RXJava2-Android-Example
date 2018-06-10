package com.anurag.flickr.loader;

import android.content.res.Resources;

import com.anurag.flickr.BuildConfig;
import com.anurag.flickr.Repository.RecentPhotosRepository;
import com.anurag.flickr.event.photo.GetRecentPhotosSuccessResponseEvent;
import com.anurag.flickr.fake.RecordingEventBus;
import com.anurag.flickr.model.GetRecentPhotosResponse;
import com.anurag.flickr.network.NetworkManager;
import com.anurag.flickr.util.TestResourceReaderUtil;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import retrofit2.Callback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test Class for GetPhotoLoader
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetPhotoLoaderTest {
    @Mock
    private NetworkManager mMockNetworkManager;
    @Mock
    private RecentPhotosRepository mMockRecentPhotosRepository;

    private Resources mResources;
    private RecordingEventBus mRecordingEventBus = new RecordingEventBus();
    private ClassLoader mClassLoader;
    private GetPhotoNetworkLoader mGetPhotoNetworkLoader;

    @Before
    public void setUp() throws Exception {
        mResources = RuntimeEnvironment.application.getResources();
        mClassLoader = this.getClass().getClassLoader();

        MockitoAnnotations.initMocks(this);
        mGetPhotoNetworkLoader = new GetPhotoNetworkLoader(mMockNetworkManager,
                mRecordingEventBus, mResources, mMockRecentPhotosRepository) {
        };
    }


    @Test
    public void testJsonFileLoading() {
        // Given


        // When
        String loadedJsonString = TestResourceReaderUtil.readFile(mClassLoader,
                "get_recent_photos_sample_client_response.json");

        // Then
        assertThat(loadedJsonString, instanceOf(String.class));
    }

    @Test
    public void testPage0ReturnsBackPersistedResponse() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment &
        when(mMockRecentPhotosRepository.getRecentPhotosLastResponse())
                .thenReturn(TestResourceReaderUtil.readFile(mClassLoader,
                        "get_recent_photos_sample_client_response.json"));

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(0);

        // Then
        verify(mMockRecentPhotosRepository, times(1))
                .getRecentPhotosLastResponse();
    }

    @Test
    public void testPersistedResponseIsJsonStringOfTypeClientResponse() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment &
        when(mMockRecentPhotosRepository.getRecentPhotosLastResponse())
                .thenReturn(TestResourceReaderUtil.readFile(mClassLoader,
                        "get_recent_photos_sample_client_response.json"));

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(0);

        // Then
        String actual = mMockRecentPhotosRepository.getRecentPhotosLastResponse();
        assertThat(actual, instanceOf(String.class));

        /*
        *  Trying to convert to GetRecentPhotoResponse object, JsonSyntaxException would be thrown
        *  if string is not of desired object type.
        */
        new Gson().fromJson(actual, GetRecentPhotosResponse.class);
    }

    @Test
    public void testEventFiredHasLastSavedResponseWhenPageIs0() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment &
        when(mMockRecentPhotosRepository.getRecentPhotosLastResponse())
                .thenReturn(TestResourceReaderUtil.readFile(mClassLoader,
                        "get_recent_photos_sample_client_response.json"));

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(0);
        //String lastSavedResponse = mMockRecentPhotosRepository.getRecentPhotosLastResponse();

        // Then perform assertions that correct event was fired
        GetRecentPhotosSuccessResponseEvent event = (GetRecentPhotosSuccessResponseEvent) mRecordingEventBus
                .getLastPostedEvent();

        assertThat(event.isNetworkResponse(), equalTo(false));
        assertThat(event.getResponse().getPage(), equalTo(1));
        assertThat(event.getResponse().getPages(), equalTo(10));
        assertThat(event.getResponse().getPerPage(), equalTo(100));
        assertThat(event.getResponse().getTotal(), equalTo(1000));
        assertThat(event.getResponse().getPhotoList()
                .get(0).getTitle(), equalTo("Compact and Stylish Leather Couch and Loveseat"));
        assertThat(event.getResponse().getPhotoList()
                        .get(0).getUrl(),
                equalTo("https://farm1.staticflickr.com/952/27335869967_c703a3469b_b.jpg"));
    }

    @Test
    public void testCorrectPageIsBeingPassedForMakingNetworkCall() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(8);
        //String lastSavedResponse = mMockRecentPhotosRepository.getRecentPhotosLastResponse();

        // Then perform assertions that correct event was fired
        verify(mMockNetworkManager, times(1))
                .getRecentPhotos(eq(8), any(Callback.class));
    }

    @Test
    public void testGetRecentPhotosFromMemoryInvokesCallToServiceWithPageValue1() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(0);

        // Then
        verify(mMockNetworkManager, times(1))
                .getRecentPhotos(eq(1), any(GetPhotoNetworkLoader.class));
    }

    @Test
    public void testPageGreaterThan0DoesntInvokeRecentPhotosRepository() {
        // Given mGetPhotoNetworkLoader has been injected to the desired activity/fragment

        // When
        mGetPhotoNetworkLoader.getRecentPhotos(1);

        // Then
        verifyZeroInteractions(mMockRecentPhotosRepository);
    }

    @Ignore
    // TODO: Check how to wrap Retrofit 2.x callbacks
    public void testNetworkOnSuccessRepositoryInteraction() {
        // When
        mMockNetworkManager.getRecentPhotos(1, eq(any(GetPhotoNetworkLoader.class)));

        // Then
       // mGetPhotoNetworkLoader.onResponse(mMockedCall, mMockedResponse);

        // when
        verify(mMockRecentPhotosRepository, times(1))
                .saveRecentPhotosLastResponse(any(String.class));
    }
}
