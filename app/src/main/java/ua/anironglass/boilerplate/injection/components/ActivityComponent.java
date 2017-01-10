package ua.anironglass.boilerplate.injection.components;

import dagger.Subcomponent;
import ua.anironglass.boilerplate.injection.modules.ActivityModule;
import ua.anironglass.boilerplate.injection.scopes.PerActivity;
import ua.anironglass.boilerplate.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}