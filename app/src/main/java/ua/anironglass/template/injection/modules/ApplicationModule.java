package ua.anironglass.template.injection.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.template.injection.ApplicationContext;
import ua.anironglass.template.injection.ApplicationInstance;


/**
 * Provide application-level dependencies.
 */
@Module
public final class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @NonNull
    @Provides
    @ApplicationInstance
    Application provideApplication() {
        return mApplication;
    }

    @NonNull
    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

}