package ua.anironglass.template.injection.modules;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ua.anironglass.template.injection.ActivityContext;


@Module
public final class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @NonNull
    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
}