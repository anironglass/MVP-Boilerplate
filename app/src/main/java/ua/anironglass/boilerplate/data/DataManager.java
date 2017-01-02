package ua.anironglass.boilerplate.data;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import ua.anironglass.boilerplate.data.local.DatabaseHelper;
import ua.anironglass.boilerplate.data.local.PreferencesHelper;
import ua.anironglass.boilerplate.data.model.Photo;
import ua.anironglass.boilerplate.data.remote.ApiService;


@Singleton
public class DataManager {

    private static final int RETRY_COUNT_FOR_REQUEST = 3;
    private static final int TIMEOUT_IN_SECONDS = 15;

    private final ApiService mApiService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    @SuppressWarnings("WeakerAccess")
    public DataManager(@NonNull ApiService apiService,
                       @NonNull DatabaseHelper databaseHelper,
                       @NonNull PreferencesHelper preferencesHelper) {
        mApiService = apiService;
        mDatabaseHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @NonNull
    public Observable<List<Photo>> getPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("Loading local photos, album %d", albumId);
        return mDatabaseHelper.getPhotos(albumId);
    }

    @NonNull
    public Observable<List<Photo>> syncPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("Loading remote photos, album %d", albumId);
        return mApiService.getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .doOnNext(photos -> Timber.d(
                        "[%s] Loaded %d remote photos from server (album = %d)",
                        Thread.currentThread().getName(),
                        photos.size(),
                        albumId))
                .concatMap(mDatabaseHelper::setPhotos)
                .retry(RETRY_COUNT_FOR_REQUEST)
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
    }

}