package ua.anironglass.template.injection.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.data.remote.ApiHelper;
import ua.anironglass.template.injection.ApplicationContext;
import ua.anironglass.template.injection.ApplicationInstance;
import ua.anironglass.template.injection.modules.ApplicationModule;
import ua.anironglass.template.utils.LeakCanaryHelper;
import ua.anironglass.template.utils.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext Context context();
    @ApplicationInstance Application application();
    ApiHelper apiHelper();
    RxEventBus eventBus();
    LeakCanaryHelper leakCanary();
    PreferencesHelper preferencesHelper();

}