package ua.anironglass.template.ui.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ua.anironglass.template.R;
import ua.anironglass.template.data.SyncService;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.activities.base.BaseActivity;
import ua.anironglass.template.utils.LeakCanaryHelper;
import ua.anironglass.template.utils.SnackBarHelper;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "ua.anironglass.template.ui.activities.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.content_view) ConstraintLayout mContentView;
    @Inject LeakCanaryHelper mLeakCanary;
    @Inject MainPresenter mMainPresenter;
    @Inject PhotosAdapter mPhotosAdapter;
    @Inject SnackBarHelper mSnackBarHelper;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(@NonNull Context context, boolean shouldSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, shouldSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("MainActivity created");
        getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeView();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }

        mLeakCanary.watch(this);
    }

    @Override
    public void onResume() {
        Timber.d("MainActivity resumed");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Timber.d("MainActivity paused");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Timber.d("MainActivity destroyed");
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        mPhotosAdapter.addPhotos(photos);
        mPhotosAdapter.notifyDataSetChanged();

        mSnackBarHelper.showShort(
                mContentView,
                String.format(Locale.getDefault(), "Loaded %d photos", photos.size()));

        mLeakCanary.watch(photos);
    }

    private void initializeView() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPhotosAdapter);
        mMainPresenter.attachView(this);
    }

}