package ua.anironglass.boilerplate.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.boilerplate.injection.components.ApplicationComponent;
import ua.anironglass.boilerplate.injection.module.ApplicationTestModule;


@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}