package ua.anironglass.template.injection.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.template.data.remote.ApiService;
import ua.anironglass.template.injection.ApplicationContext;


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
    Application provideApplication() {
        return mApplication;
    }

    @NonNull
    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @NonNull
    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Builder.newApiService();
    }

}