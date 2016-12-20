package ua.anironglass.template.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ua.anironglass.template.R;
import ua.anironglass.template.data.SyncService;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.base.BaseActivity;
import ua.anironglass.template.utils.SnackBarHelper;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "ua.anironglass.template.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.content_view) ConstraintLayout mContentView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
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

        initializeToolbar();
        initializeRecyclerView();
        initializeSwipeRefreshing();
        initializePresenter();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
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

    private void initializeToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(R.string.title_main);
            actionBar.setSubtitle(R.string.app_name);
        }
    }

    private void initializeRecyclerView() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPhotosAdapter);
    }

    private void initializeSwipeRefreshing() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mMainPresenter.syncPhotos());
    }

    private void initializePresenter() {
        mMainPresenter.attachView(this);
        mMainPresenter.getPhotos();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showError() {
        mSwipeRefreshLayout.setRefreshing(false);
        //TODO Add implementation
    }

    @Override
    public void showNoInternetConnection() {
        mSwipeRefreshLayout.setRefreshing(false);
        //TODO Add implementation
    }

    @Override
    public void showPhotosEmpty() {
        mSwipeRefreshLayout.setRefreshing(false);
        //TODO Add implementation
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        Timber.d("Showed %d photos", photos.size());
        mSwipeRefreshLayout.setRefreshing(false);
        mPhotosAdapter.setPhotos(photos);
    }

}