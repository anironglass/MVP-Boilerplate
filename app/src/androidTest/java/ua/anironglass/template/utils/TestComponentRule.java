package ua.anironglass.template.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import ua.anironglass.template.App;
import ua.anironglass.template.common.injection.component.DaggerTestComponent;
import ua.anironglass.template.common.injection.component.TestComponent;
import ua.anironglass.template.common.injection.module.ApplicationTestModule;
import ua.anironglass.template.data.DataManager;


/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private final TestComponent mTestComponent;
    private final Context mContext;

    public TestComponentRule(@NonNull Context context) {
        mContext = context;
        App application = App.get(context);
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
    }

    @Override
    public Statement apply(@NonNull final Statement base, @NonNull Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                App application = App.get(mContext);
                application.setComponent(mTestComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    @NonNull
    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

}