package ua.anironglass.template.injection.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.template.data.DataManager;
import ua.anironglass.template.data.SyncService;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.injection.ApplicationContext;
import ua.anironglass.template.injection.modules.ApplicationModule;
import ua.anironglass.template.utils.NetworkStateHelper;
import ua.anironglass.template.utils.NetworkUtils;
import ua.anironglass.template.utils.RxEventBus;

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