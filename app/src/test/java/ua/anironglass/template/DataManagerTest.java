package ua.anironglass.template;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.observers.TestSubscriber;
import ua.anironglass.template.common.TestDataFactory;
import ua.anironglass.template.data.DataManager;
import ua.anironglass.template.data.local.DatabaseHelper;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.data.remote.ApiService;
import ua.anironglass.template.utils.RxUtils;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    private static final int TEST_ALBUM_ID = 99;

    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock PreferencesHelper mMockPrefsHelper;
    @Mock ApiService mMockApiService;
    private DataManager mDataManager;
    private Subscription mSubscription;

    @Before
    public void initialize() {
        mDataManager = new DataManager(mMockApiService, mMockDatabaseHelper, mMockPrefsHelper);
    }

    @After
    public void close() {
        RxUtils.unsubscribe(mSubscription);
    }

    @Test
    public void shouldEmitsRemoteValues() {
        // Initialize: return remote test photos observable
        List<Photo> photos = TestDataFactory.getRandomPhotos();
        when(mMockPrefsHelper.getAlbumId())
                .thenReturn(TEST_ALBUM_ID);
        when(mMockApiService.getPhotos(TEST_ALBUM_ID))
                .thenReturn(Observable.just(photos));
        when(mMockDatabaseHelper.setPhotos(photos))
                .thenReturn(Observable.just(photos));

        // Run: sync photos and wait for result
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mSubscription = mDataManager.syncPhotos()
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: should emit photos
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(photos);
        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldEmitsLocalValues() {
        // Initialize: return local test photos observable
        List<Photo> photos = TestDataFactory.getRandomPhotos();
        when(mMockPrefsHelper.getAlbumId())
                .thenReturn(TEST_ALBUM_ID);
        when(mMockDatabaseHelper.getPhotos(TEST_ALBUM_ID))
                .thenReturn(Observable.just(photos));

        // Run: get local photos and wait for result
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mSubscription = mDataManager.getPhotos()
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: should emit photos
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(photos);
        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldCallsDatabaseWhenGettingPhoto() {
        // Initialize: return remote test photos observable
        List<Photo> photos = TestDataFactory.getRandomPhotos();
        when(mMockPrefsHelper.getAlbumId())
                .thenReturn(TEST_ALBUM_ID);
        when(mMockDatabaseHelper.getPhotos(TEST_ALBUM_ID))
                .thenReturn(Observable.just(photos));

        // Run: sync photos and wait for result
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mSubscription = mDataManager.getPhotos()
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: should call database
        verify(mMockApiService, never()).getPhotos(TEST_ALBUM_ID);
        verify(mMockDatabaseHelper).getPhotos(TEST_ALBUM_ID);
    }

    @Test
    public void shouldCallsApiAndDatabaseWhenSyncingPhoto() {
        // Initialize: return remote test photos observable
        List<Photo> photos = TestDataFactory.getRandomPhotos();
        when(mMockPrefsHelper.getAlbumId())
                .thenReturn(TEST_ALBUM_ID);
        when(mMockApiService.getPhotos(TEST_ALBUM_ID))
                .thenReturn(Observable.just(photos));
        when(mMockDatabaseHelper.setPhotos(photos))
                .thenReturn(Observable.just(photos));

        // Run: sync photos and wait for result
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mSubscription = mDataManager.syncPhotos()
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: should call api and database
        verify(mMockApiService).getPhotos(TEST_ALBUM_ID);
        verify(mMockDatabaseHelper).setPhotos(photos);
    }

    @Test
    public void shouldNotCallDatabaseWhenApiFails() {
        // Initialize: return error observable when synced
        when(mMockPrefsHelper.getAlbumId())
                .thenReturn(TEST_ALBUM_ID);
        when(mMockApiService.getPhotos(TEST_ALBUM_ID))
                .thenReturn(Observable.error(new RuntimeException()));

        // Run: sync photos and wait for result
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mSubscription = mDataManager.syncPhotos()
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: shouldn't call database after api fail
        verify(mMockApiService).getPhotos(TEST_ALBUM_ID);
        verify(mMockDatabaseHelper, never()).setPhotos(ArgumentMatchers.anyCollection());
    }

}