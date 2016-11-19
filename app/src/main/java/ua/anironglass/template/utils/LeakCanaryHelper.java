package ua.anironglass.template.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.AndroidHeapDumper;
import com.squareup.leakcanary.DefaultLeakDirectoryProvider;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.HeapDumper;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.LeakDirectoryProvider;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.template.BuildConfig;
import ua.anironglass.template.injection.ApplicationContext;


@Singleton
public final class LeakCanaryHelper {

    private RefWatcher mRefWatcher;
    private ToggleableHeapDumper mToggleableHeapDumper;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public LeakCanaryHelper(@ApplicationContext Context context) {
        if (LeakCanary.isInAnalyzerProcess(context)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (BuildConfig.DEBUG) {
            // Ignore known Android SDK leaks
            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                    .instanceField("android.database.ContentObserver$Transport", "mContentObserver")
                    .build();

            LeakDirectoryProvider provider = new DefaultLeakDirectoryProvider(context);
            AndroidHeapDumper defaultDumper = new AndroidHeapDumper(context, provider);
            mToggleableHeapDumper = new ToggleableHeapDumper(defaultDumper);

            mRefWatcher = LeakCanary.refWatcher(context)
                    .excludedRefs(excludedRefs)
                    .heapDumper(mToggleableHeapDumper)
                    .buildAndInstall();
        } else {
            mRefWatcher = RefWatcher.DISABLED;
        }

    }

    public void watch(@NonNull Object watchedReference, @NonNull String referenceName) {
        if (null == mRefWatcher) {
            return;
        }
        mRefWatcher.watch(watchedReference, referenceName);
    }

    public void watch(@NonNull Object watchedReference) {
        if (null == mRefWatcher) {
            return;
        }
        mRefWatcher.watch(watchedReference);
    }

    /**
     * Toggling LeakCanary on and off at runtime
     */
    public boolean toggleHeapDumper() {
        if (null == mToggleableHeapDumper) {
            return false;
        }
        mToggleableHeapDumper.toggle();
        return true;
    }

    private static class ToggleableHeapDumper implements HeapDumper {

        private final HeapDumper mDefaultDumper;
        private boolean mIsEnabled = true;

        private ToggleableHeapDumper(@NonNull HeapDumper defaultDumper) {
            mDefaultDumper = defaultDumper;
        }

        private void toggle() {
            mIsEnabled = !mIsEnabled;
        }

        @Override
        public File dumpHeap() {
            return mIsEnabled
                    ? mDefaultDumper.dumpHeap()
                    : HeapDumper.RETRY_LATER;
        }
    }

}