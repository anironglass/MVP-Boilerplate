package ua.anironglass.template.ui.activities.main;


import android.support.annotation.NonNull;

import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;
import ua.anironglass.template.data.DataManager;
import ua.anironglass.template.injection.ConfigPersistent;
import ua.anironglass.template.ui.activities.base.BasePresenter;
import ua.anironglass.template.utils.RxUtils;

@ConfigPersistent
class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mLoadPhotosSubscription;

    @Inject
    MainPresenter(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        Timber.d("View attached to MainPresenter");
    }

    @Override
    public void detachView() {
        super.detachView();
        Timber.d("View detached from MainPresenter");
        RxUtils.unsubscribe(mLoadPhotosSubscription);
    }

    void loadPhotos() {
        Timber.d("Starting loading photos");
        checkViewAttached();
        RxUtils.unsubscribe(mLoadPhotosSubscription);
        mLoadPhotosSubscription = mDataManager.getPhotos()
                .compose(RxLifecycleAndroid.bindActivity(getMvpView().getLifecycle()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        photos -> getMvpView().showPhotos(photos),
                        error -> Timber.e(error, "There was an error loading the photos")
                );
    }

}