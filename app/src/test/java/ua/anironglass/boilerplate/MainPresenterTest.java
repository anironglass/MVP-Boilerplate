package ua.anironglass.boilerplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import ua.anironglass.boilerplate.common.TestDataFactory;
import ua.anironglass.boilerplate.common.TestLifecycleFactory;
import ua.anironglass.boilerplate.common.TestNetworkUtils;
import ua.anironglass.boilerplate.data.DataManager;
import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.ui.main.MainMvpView;
import ua.anironglass.boilerplate.ui.main.MainPresenter;
import ua.anironglass.boilerplate.utils.RxSchedulersRule;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public final class MainPresenterTest {

    @Mock DataManager mMockDataManager;
    @Mock MainMvpView mMockMainMvpView;
    private MainPresenter mMainPresenter;
    private TestNetworkUtils mTestNetworkUtils;

    @Rule public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Before
    public void initialize() {
        mTestNetworkUtils = new TestNetworkUtils();
        mMainPresenter = new MainPresenter(mMockDataManager, mTestNetworkUtils);

        when(mMockMainMvpView.getLifecycle())
                .thenReturn(TestLifecycleFactory.getLifecycle());

        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void close() {
        mMainPresenter.detachView();
    }

    @Test
    public void shouldShowErrorWhenRequestLocalPhotos() {
        // Initialize: turn internet connection ON and return error observable on photos request
        mTestNetworkUtils.setConnected(true);
        when(mMockDataManager.getPhotos())
                .thenReturn(Observable.error(new RuntimeException()));

        // Run: request local photos
        mMainPresenter.getPhotos();

        // Check: should call only showError() method
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView, never()).showPhotosEmpty();
        verify(mMockMainMvpView, never()).showPhotos(ArgumentMatchers.anyList());
    }

    @Test
    public void shouldShowErrorWhenRequestRemotePhotos() {
        // Initialize: turn internet connection ON and return error observable on photos request
        mTestNetworkUtils.setConnected(true);
        when(mMockDataManager.syncPhotos())
                .thenReturn(Observable.error(new RuntimeException()));

        // Run: request local photos
        mMainPresenter.syncPhotos();

        // Check: should call only showError() method
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView, never()).showPhotosEmpty();
        verify(mMockMainMvpView, never()).showPhotos(ArgumentMatchers.anyList());
    }

    @Test
    public void shouldShowNoInternetConnection() {
        // Initialize: turn internet connection OFF
        mTestNetworkUtils.setConnected(false);

        // Run: request remote photos
        mMainPresenter.syncPhotos();

        // Check: should call only showNoInternetConnection() method
        verify(mMockMainMvpView, never()).showError();
        verify(mMockMainMvpView).showNoInternetConnection();
        verify(mMockMainMvpView, never()).showPhotosEmpty();
        verify(mMockMainMvpView, never()).showPhotos(ArgumentMatchers.anyList());
    }

    @Test
    public void shouldShowEmptyPhotosWhenRequestLocalPhotos() {
        // Initialize: turn internet connection ON and return empty observable on photos request
        mTestNetworkUtils.setConnected(true);
        when(mMockDataManager.getPhotos())
                .thenReturn(Observable.just(Collections.emptyList()));

        // Run: request local photos
        mMainPresenter.getPhotos();

        // Check: should call only showPhotosEmpty() method
        verify(mMockMainMvpView, never()).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView).showPhotosEmpty();
        verify(mMockMainMvpView, never()).showPhotos(ArgumentMatchers.anyList());
    }

    @Test
    public void shouldShowEmptyPhotosWhenRequestRemotePhotos() {
        // Initialize: turn internet connection ON and return empty observable on photos request
        mTestNetworkUtils.setConnected(true);
        when(mMockDataManager.syncPhotos())
                .thenReturn(Observable.just(Collections.emptyList()));

        // Run: request remote photos
        mMainPresenter.syncPhotos();

        // Check: should call only showPhotosEmpty() method
        verify(mMockMainMvpView, never()).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView).showPhotosEmpty();
        verify(mMockMainMvpView, never()).showPhotos(ArgumentMatchers.anyList());
    }

    @Test
    public void shouldShowPhotosWhenRequestLocalPhotos() {
        // Initialize: turn internet connection ON and return test observable on photos request
        mTestNetworkUtils.setConnected(true);
        List<Photo> testPhotos = TestDataFactory.getRandomPhotos();
        when(mMockDataManager.getPhotos())
                .thenReturn(Observable.just(testPhotos));

        // Run: request local photos
        mMainPresenter.getPhotos();

        // Check: should call only showPhotos(testPhotos) method
        verify(mMockMainMvpView, never()).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView, never()).showPhotosEmpty();
        verify(mMockMainMvpView).showPhotos(testPhotos);
    }

    @Test
    public void shouldShowPhotosWhenRequestRemotePhotos() {
        // Initialize: turn internet connection ON and return test observable on photos request
        mTestNetworkUtils.setConnected(true);
        List<Photo> testPhotos = TestDataFactory.getRandomPhotos();
        when(mMockDataManager.syncPhotos())
                .thenReturn(Observable.just(testPhotos));

        // Run: request remote photos
        mMainPresenter.syncPhotos();

        // Check: should call only showPhotos(testPhotos) method
        verify(mMockMainMvpView, never()).showError();
        verify(mMockMainMvpView, never()).showNoInternetConnection();
        verify(mMockMainMvpView, never()).showPhotosEmpty();
        verify(mMockMainMvpView).showPhotos(testPhotos);
    }

}