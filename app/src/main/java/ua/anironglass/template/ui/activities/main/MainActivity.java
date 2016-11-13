package ua.anironglass.template.ui.activities.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ua.anironglass.template.R;
import ua.anironglass.template.ui.activities.base.BaseActivity;
import ua.anironglass.template.utils.LeakCanaryHelper;

public class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @Inject MainPresenter mainPresenter;
    @Inject LeakCanaryHelper leakCanary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Create MainActivity");
        getComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeView();

        leakCanary.watch(this);
    }

    @Override
    public void onResume() {
        Timber.d("Resume MainActivity");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Timber.d("Pause MainActivity");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Timber.d("Destroy MainActivity");
        super.onDestroy();
        mainPresenter.detachView();
    }

    private void initializeView() {
        mainPresenter.attachView(this);
    }

}