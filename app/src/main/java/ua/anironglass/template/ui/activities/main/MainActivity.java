package ua.anironglass.template.ui.activities.main;

import android.os.Bundle;

import javax.inject.Inject;

import timber.log.Timber;
import ua.anironglass.template.R;
import ua.anironglass.template.ui.activities.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Create MainActivity");
        getComponent().inject(this);
        setContentView(R.layout.activity_main);

        initializeView();
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