package ua.anironglass.boilerplate.injection.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.boilerplate.App;
import ua.anironglass.boilerplate.data.remote.ApiService;
import ua.anironglass.boilerplate.injection.qualifiers.ApplicationContext;


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

    @NonNull
    @Provides
    @Singleton
    RefWatcher provideRefWatcher(@ApplicationContext Context context) {
        return App.get(context).getRefWatcher();
    }

}