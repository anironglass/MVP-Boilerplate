package ua.anironglass.template.utils;

import android.app.Application;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public final class LeakCanaryHelper {

    private RefWatcher mRefWatcher;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public LeakCanaryHelper(@NonNull Application application) {
        mRefWatcher = LeakCanary.install(application);
    }

    public void watch(@NonNull Object watchedReference) {
        mRefWatcher.watch(watchedReference);
    }


    public void watch(@NonNull Object watchedReference, @NonNull String referenceName) {
        mRefWatcher.watch(watchedReference, referenceName);
    }

}