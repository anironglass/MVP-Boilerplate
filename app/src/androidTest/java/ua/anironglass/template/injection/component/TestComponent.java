package ua.anironglass.template.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.template.injection.components.ApplicationComponent;
import ua.anironglass.template.injection.module.ApplicationTestModule;


@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}