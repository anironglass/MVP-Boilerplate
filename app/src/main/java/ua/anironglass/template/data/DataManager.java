package ua.anironglass.template.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import timber.log.Timber;
import ua.anironglass.template.data.local.DatabaseHelper;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.data.remote.ApiHelper;


@Singleton
public class DataManager {

    private final ApiHelper mApiHelper;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in activity singleton
    public DataManager(@NonNull ApiHelper apiHelper,
                       @NonNull DatabaseHelper databaseHelper,
                       @NonNull PreferencesHelper preferencesHelper) {
        mApiHelper = apiHelper;
        mDatabaseHelper = databaseHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @NonNull
    public Observable<List<Photo>> getPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("Get photos from album %d", albumId);
        return mDatabaseHelper.getPhotos(albumId)
                .distinct();
    }

    @NonNull
    Observable<Photo> syncPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("Sync photos from album %d", albumId);
        return mApiHelper.getPhotos(albumId)
                .concatMap(mDatabaseHelper::setPhotos);
    }

}