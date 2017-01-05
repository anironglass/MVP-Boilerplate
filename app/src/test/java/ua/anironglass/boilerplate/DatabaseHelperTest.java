package ua.anironglass.boilerplate;

import android.database.Cursor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import ua.anironglass.boilerplate.data.local.Database;
import ua.anironglass.boilerplate.data.local.DatabaseHelper;
import ua.anironglass.boilerplate.data.local.DbOpenHelper;
import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.utils.RobolectricDefaultConfig;
import ua.anironglass.boilerplate.utils.RxSchedulersRule;
import ua.anironglass.boilerplate.utils.TestDataFactory;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(
        application = TestApp.class,
        constants = BuildConfig.class,
        sdk = RobolectricDefaultConfig.EMULATE_SDK
)
public final class DatabaseHelperTest {

    private static final int TEST_ALBUM_ID = 99;
    private static final long TIMEOUT_MILLIS = 1000;

    private final DatabaseHelper mDatabaseHelper =
            new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application));

    @Rule public final RxSchedulersRule mOverrideSchedulersRule = new RxSchedulersRule();

    @Test
    public void shouldSetPhotos() {
        // Initialize: prepare test photos
        Photo photo1 = TestDataFactory.getRandomPhoto("photo_1", TEST_ALBUM_ID);
        Photo photo2 = TestDataFactory.getRandomPhoto("photo_2", TEST_ALBUM_ID);
        Photo photo3 = TestDataFactory.getRandomPhoto("photo_3", TEST_ALBUM_ID);
        List<Photo> testPhotos = Arrays.asList(photo1, photo2, photo3);

        // Run: save test photos
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mDatabaseHelper.setPhotos(testPhotos)
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // Check: should emit photos
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(testPhotos);
        testSubscriber.assertCompleted();

        // Run: read cached photos
        Cursor cursor = mDatabaseHelper.getDatabase()
                .query("SELECT * FROM " + Database.PhotosTable.TABLE_NAME);
        List<Photo> cachedPhotos = new ArrayList<>();
        while (cursor.moveToNext()) {
            cachedPhotos.add(Database.PhotosTable.parseCursor(cursor));
        }

        // Check: cached photos should match saved photos
        assertThat(cachedPhotos)
                .containsExactlyElementsIn(testPhotos)
                .inOrder();
    }

    @Test
    public void shouldGetPhotos() {
        // Initialize: prepare test photos
        Photo photo1 = TestDataFactory.getRandomPhoto("photo_1", TEST_ALBUM_ID);
        Photo photo2 = TestDataFactory.getRandomPhoto("photo_2", TEST_ALBUM_ID);
        Photo photo3 = TestDataFactory.getRandomPhoto("photo_3", TEST_ALBUM_ID);
        List<Photo> testPhotos = Arrays.asList(photo1, photo2, photo3);

        // Run: save and read test photos
        mDatabaseHelper.setPhotos(testPhotos)
                .subscribe();
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mDatabaseHelper.getPhotos(TEST_ALBUM_ID)
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        // Check: emit values
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(testPhotos);
        testSubscriber.assertNotCompleted();
    }

    @Test
    public void shouldEmitPhotosAfterEachSave() {
        // Initialize: prepare test photos
        Photo photo1 = TestDataFactory.getRandomPhoto("photo_1", TEST_ALBUM_ID);
        Photo photo2 = TestDataFactory.getRandomPhoto("photo_2", TEST_ALBUM_ID);
        List<Photo> testPhotos = Arrays.asList(photo1, photo2);

        Photo photo3 = TestDataFactory.getRandomPhoto("photo_3", TEST_ALBUM_ID);
        Photo photo4 = TestDataFactory.getRandomPhoto("photo_4", TEST_ALBUM_ID);
        List<Photo> anotherTestPhotos = Arrays.asList(photo3, photo4);

        // Run: subscribe photos changes
        TestSubscriber<List<Photo>> testSubscriber = new TestSubscriber<>();
        mDatabaseHelper.getPhotos(TEST_ALBUM_ID)
                .subscribe(testSubscriber);

        // Run: save photos
        mDatabaseHelper.setPhotos(testPhotos)
                .subscribe();
        mDatabaseHelper.setPhotos(anotherTestPhotos)
                .subscribe();
        testSubscriber.awaitTerminalEvent(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        // Check: emit each photo
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(
                Collections.emptyList(),
                testPhotos,
                anotherTestPhotos
        ));
        testSubscriber.assertNotCompleted();
    }

}