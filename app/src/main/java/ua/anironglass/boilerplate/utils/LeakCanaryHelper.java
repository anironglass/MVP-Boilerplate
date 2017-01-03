package ua.anironglass.boilerplate.utils;

import android.support.annotation.NonNull;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public final class LeakCanaryHelper {

    private final RefWatcher mRefWatcher;

    @Inject
    @SuppressWarnings("WeakerAccess")
    public LeakCanaryHelper(@NonNull RefWatcher refWatcher) {
        mRefWatcher = refWatcher;
    }

    public void watch(@NonNull Object watchedReference, @NonNull String referenceName) {
        mRefWatcher.watch(watchedReference, referenceName);
    }

    public void watch(@NonNull Object watchedReference) {
        mRefWatcher.watch(watchedReference);
    }

}