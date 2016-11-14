package ua.anironglass.template.ui.activities.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ua.anironglass.template.R;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.ui.activities.base.BaseActivity;
import ua.anironglass.template.utils.LeakCanaryHelper;

public class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @Inject LeakCanaryHelper leakCanary;
    @Inject MainPresenter mainPresenter;
    @Inject PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("MainActivity created");
        getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeView();

        leakCanary.watch(this);
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
        mainPresenter.detachView();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        photosAdapter.addPhotos(photos);
        photosAdapter.notifyDataSetChanged();

        leakCanary.watch(photos);
    }

    private void initializeView() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(photosAdapter);
        mainPresenter.attachView(this);

        leakCanary.watch(recyclerView);
        leakCanary.watch(photosAdapter);
        leakCanary.watch(mainPresenter);
    }

}