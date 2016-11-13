package ua.anironglass.template;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ua.anironglass.template.data.local.PreferencesHelper;
import ua.anironglass.template.utils.DefaultConfig;

import static com.google.common.truth.Truth.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = DefaultConfig.EMULATE_SDK)
public final class PreferencesTest {

    private static final String TEST_SOMETHING = "test_something";

    private PreferencesHelper mPreferencesHelper;

    @Before
    public void initialize() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        mPreferencesHelper = new PreferencesHelper(context);
    }

    @Test
    public void shouldSetSomething() {
        mPreferencesHelper.setSomething(TEST_SOMETHING);
        assertThat(mPreferencesHelper.getSomething())
                .matches(TEST_SOMETHING);
    }

    @Test
    public void shouldMatchDefaults() {
        assertThat(mPreferencesHelper.getSomething())
                .matches(PreferencesHelper.DEFAULT_SOMETHING);
    }

}