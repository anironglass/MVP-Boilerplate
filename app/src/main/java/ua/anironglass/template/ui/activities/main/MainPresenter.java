package ua.anironglass.template.ui.activities.main;


import timber.log.Timber;
import ua.anironglass.template.ui.activities.base.BasePresenter;

class MainPresenter extends BasePresenter<MainMvpView> {


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