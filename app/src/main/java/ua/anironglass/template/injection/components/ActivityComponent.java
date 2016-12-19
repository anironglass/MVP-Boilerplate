package ua.anironglass.template.injection.components;

import dagger.Subcomponent;
import ua.anironglass.template.injection.PerActivity;
import ua.anironglass.template.injection.modules.ActivityModule;
import ua.anironglass.template.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}