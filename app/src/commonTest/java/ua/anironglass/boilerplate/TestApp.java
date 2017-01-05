package ua.anironglass.boilerplate;


import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import ua.anironglass.boilerplate.injection.component.DaggerTestApplicationComponent;
import ua.anironglass.boilerplate.injection.components.ApplicationComponent;
import ua.anironglass.boilerplate.injection.module.TestApplicationModule;

public class TestApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new UnlockDeviceActivityLifecycleCallbacks());
    }

    @Override
    public ApplicationComponent getComponent() {
        if (null == mApplicationComponent) {
            mApplicationComponent = DaggerTestApplicationComponent.builder()
                    .testApplicationModule(new TestApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }


    private class UnlockDeviceActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
            activity.getWindow()
                    .setFlags(flags, flags);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }

}