package ua.anironglass.template.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.template.common.injection.module.ApplicationTestModule;
import ua.anironglass.template.injection.components.ApplicationComponent;


@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}