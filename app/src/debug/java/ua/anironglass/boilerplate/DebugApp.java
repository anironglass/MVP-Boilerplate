package ua.anironglass.boilerplate;

import android.os.StrictMode;

import timber.log.Timber;


public class DebugApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeStrictMode();
        initializeTimber();
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

    private void initializeTimber() {
        Timber.plant(new Timber.DebugTree());
    }

}