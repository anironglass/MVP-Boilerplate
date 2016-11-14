package ua.anironglass.template.data.remote;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.injection.ApiServiceInstance;
import ua.anironglass.template.utils.LogHelper;

@Singleton
public class ApiHelper {

    private static final int RETRY_COUNT_FOR_REQUEST = 3;
    private static final int TIMEOUT_IN_SECONDS = 15;

    private ApiService mApiService;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public ApiHelper(@ApiServiceInstance ApiService apiService) {
        mApiService = apiService;
    }

    @NonNull
    public Observable<List<Photo>> getPhotos(@IntRange(from = 1, to = 100) int albumId) {
        return mApiService.getPhotos(albumId)
                .subscribeOn(Schedulers.io())
                .doOnNext(photos -> Timber.d(
                        LogHelper.attachThreadName("Loaded remote photos, albumId = %d"),
                        albumId))
                .retry(RETRY_COUNT_FOR_REQUEST)
                .timeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
    }

}