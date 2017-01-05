package ua.anironglass.boilerplate.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.boilerplate.injection.components.ApplicationComponent;
import ua.anironglass.boilerplate.injection.module.TestApplicationModule;


@Singleton
@Component(modules = TestApplicationModule.class)
public interface TestApplicationComponent extends ApplicationComponent {
}