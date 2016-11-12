package ua.anironglass.template.ui.activities.main;


import javax.inject.Inject;

import timber.log.Timber;
import ua.anironglass.template.injection.ConfigPersistent;
import ua.anironglass.template.ui.activities.base.BasePresenter;

@ConfigPersistent
class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        Timber.d("MainPresenter::attachView");
    }

    @Override
    public void detachView() {
        super.detachView();
        Timber.d("MainPresenter::detachView");
    }

}