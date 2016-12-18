package ua.anironglass.template.data;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import ua.anironglass.template.data.local.DatabaseHelper;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.data.remote.ApiService;
import ua.anironglass.template.utils.LogHelper;


@Singleton
public class DataManager {

    private static final int RETRY_COUNT_FOR_REQUEST = 3;
    private static final int TIMEOUT_IN_SECONDS = 15;

    private final ApiService mApiService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in activity singleton
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
        Timber.d("Get photos, album %d", albumId);
        return mDatabaseHelper.getPhotos(albumId)
                .distinct();
    }

    @NonNull
    Observable<Photo> syncPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("Sync photos, album %d", albumId);
        return getRemotePhotos(albumId)
                .concatMap(mDatabaseHelper::setPhotos);
    }

    @NonNull
    private Observable<List<Photo>> getRemotePhotos(@IntRange(from = 1, to = 100) int albumId) {
        return mApiService.getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .doOnNext(photos -> Timber.d(
                        LogHelper.attachThreadName("Loaded %d remote photos from server [album = %d]"),
                        photos.size(),
                        albumId))
                .retry(RETRY_COUNT_FOR_REQUEST)
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
    }

}