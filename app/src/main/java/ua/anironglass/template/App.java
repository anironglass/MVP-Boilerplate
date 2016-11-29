package ua.anironglass.template;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;
import ua.anironglass.template.injection.components.ApplicationComponent;
import ua.anironglass.template.injection.components.DaggerApplicationComponent;
import ua.anironglass.template.injection.modules.ApplicationModule;

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeStrictMode();
        initializeTimber();
    }

    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (null == mApplicationComponent) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(@Nullable ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    private void initializeStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .build();
            StrictMode.setThreadPolicy(threadPolicy);

            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build();
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}