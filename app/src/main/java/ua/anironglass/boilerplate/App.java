package ua.anironglass.boilerplate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ua.anironglass.boilerplate.injection.components.ApplicationComponent;
import ua.anironglass.boilerplate.injection.components.DaggerApplicationComponent;
import ua.anironglass.boilerplate.injection.modules.ApplicationModule;

public class App extends Application {

    private ApplicationComponent mApplicationComponent;

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

}