package ua.anironglass.template.ui.activities.base;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.atomic.AtomicLong;

import rx.Observable;
import timber.log.Timber;
import ua.anironglass.template.App;
import ua.anironglass.template.injection.components.ActivityComponent;
import ua.anironglass.template.injection.components.ConfigPersistentComponent;
import ua.anironglass.template.injection.components.DaggerConfigPersistentComponent;
import ua.anironglass.template.injection.modules.ActivityModule;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends RxAppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsArray
            = new LongSparseArray<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (null == sComponentsArray.get(mActivityId)) {
            Timber.d("Creating new ConfigPersistentComponent id = %d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(App.get(this).getComponent())
                    .build();
            sComponentsArray.put(mActivityId, configPersistentComponent);
        } else {
            Timber.d("Reusing ConfigPersistentComponent id = %d", mActivityId);
            configPersistentComponent = sComponentsArray.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.d("Clearing ConfigPersistentComponent id = %d", mActivityId);
            sComponentsArray.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent getComponent() {
        return mActivityComponent;
    }

    public Observable<ActivityEvent> getLifecycle() {
        return lifecycle();
    }
}