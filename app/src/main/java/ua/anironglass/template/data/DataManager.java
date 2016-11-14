package ua.anironglass.template.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.data.remote.ApiHelper;


@Singleton
public class DataManager {

    private final ApiHelper mApiHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in activity singleton
    public DataManager(@NonNull ApiHelper apiHelper,
                       @NonNull PreferencesHelper preferencesHelper) {
        mApiHelper = apiHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @NonNull
    public Observable<List<Photo>> getPhotos() {
        int albumId = mPreferencesHelper.getAlbumId();
        Timber.d("DataManager::getPhotos, albumId = %d", albumId);
        return mApiHelper.getPhotos(albumId)
                .observeOn(AndroidSchedulers.mainThread());
    }

}