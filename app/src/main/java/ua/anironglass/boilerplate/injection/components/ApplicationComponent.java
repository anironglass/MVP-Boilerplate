package ua.anironglass.boilerplate.injection.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.boilerplate.data.DataManager;
import ua.anironglass.boilerplate.data.SyncService;
import ua.anironglass.boilerplate.data.local.PreferencesHelper;
import ua.anironglass.boilerplate.injection.ApplicationContext;
import ua.anironglass.boilerplate.injection.modules.ApplicationModule;
import ua.anironglass.boilerplate.utils.NetworkStateHelper;
import ua.anironglass.boilerplate.utils.NetworkUtils;
import ua.anironglass.boilerplate.utils.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    DataManager dataManager();
    NetworkStateHelper networkStateHelper();
    NetworkUtils networkUtils();
    RxEventBus eventBus();
    PreferencesHelper preferencesHelper();

}