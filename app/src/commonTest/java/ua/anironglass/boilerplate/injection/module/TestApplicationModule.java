package ua.anironglass.boilerplate.injection.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.boilerplate.data.DataManager;
import ua.anironglass.boilerplate.data.remote.ApiService;
import ua.anironglass.boilerplate.injection.ApplicationContext;

import static org.mockito.Mockito.mock;


/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public final class TestApplicationModule {

    private final Application mApplication;

    public TestApplicationModule(@NonNull Application application) {
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
    RefWatcher provideRefWatcher() {
        return RefWatcher.DISABLED;
    }

    /************* MOCKS *************/

    @NonNull
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @NonNull
    @Provides
    @Singleton
    ApiService provideApiService() {
        return mock(ApiService.class);
    }

}