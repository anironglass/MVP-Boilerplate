package ua.anironglass.template.injection.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.template.data.DataManager;
import ua.anironglass.template.data.remote.ApiService;
import ua.anironglass.template.injection.ApplicationContext;

import static org.mockito.Mockito.mock;


/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule  {

    private final Application mApplication;

    public ApplicationTestModule(@NonNull Application application) {
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