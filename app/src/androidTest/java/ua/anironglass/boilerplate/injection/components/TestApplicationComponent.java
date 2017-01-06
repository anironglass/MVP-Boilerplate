package ua.anironglass.boilerplate.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.boilerplate.injection.modules.TestApplicationModule;


@Singleton
@Component(modules = TestApplicationModule.class)
public interface TestApplicationComponent extends ApplicationComponent {
}