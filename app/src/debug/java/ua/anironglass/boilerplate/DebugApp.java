package ua.anironglass.boilerplate;

import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;


public class DebugApp extends App {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeStrictMode();
        initializeLeakCanary();
        initializeTimber();
    }

    @NonNull
    @Override
    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    private void initializeStrictMode() {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .build();
        StrictMode.setThreadPolicy(threadPolicy);

        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                .detectAll()
                .build();
        StrictMode.setVmPolicy(vmPolicy);
    }

    private void initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(getApplicationContext())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            mRefWatcher = RefWatcher.DISABLED;
        } else {
            // Ignore known Android SDK leaks
            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                    .build();

            mRefWatcher = LeakCanary.refWatcher(getApplicationContext())
                    .excludedRefs(excludedRefs)
                    .buildAndInstall();
        }
    }

    private void initializeTimber() {
        Timber.plant(new Timber.DebugTree());
    }

}