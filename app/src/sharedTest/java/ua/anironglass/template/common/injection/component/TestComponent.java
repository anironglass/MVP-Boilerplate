package ua.anironglass.template.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.anironglass.template.injection.components.ApplicationComponent;
import ua.anironglass.template.test.common.injection.module.ApplicationTestModule;


@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}