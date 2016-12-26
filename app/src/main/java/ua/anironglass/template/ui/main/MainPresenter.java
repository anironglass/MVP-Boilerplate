package ua.anironglass.template.ui.main;


import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;
import ua.anironglass.template.data.DataManager;
import ua.anironglass.template.data.model.Photo;
import ua.anironglass.template.injection.ConfigPersistent;
import ua.anironglass.template.ui.base.BasePresenter;
import ua.anironglass.template.utils.NetworkUtils;
import ua.anironglass.template.utils.RxUtils;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private final NetworkUtils mNetworkUtils;
    private Subscription mLoadPhotosSubscription;

    @Inject
    public MainPresenter(@NonNull DataManager dataManager, @NonNull NetworkUtils networkUtils) {
        mDataManager = dataManager;
        mNetworkUtils = networkUtils;
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

    public void getPhotos() {
        Timber.d("Starting loading local photos...");
        checkViewAttached();
        RxUtils.unsubscribe(mLoadPhotosSubscription);
        mLoadPhotosSubscription = mDataManager.getPhotos()
                .compose(RxLifecycleAndroid.bindActivity(getMvpView().getLifecycle()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::showPhotos,
                        this::showError
                );
    }

    public void syncPhotos() {
        Timber.d("Starting loading remote photos...");
        checkViewAttached();

        if (checkNetwork()) {
            RxUtils.unsubscribe(mLoadPhotosSubscription);
            mLoadPhotosSubscription = mDataManager.syncPhotos()
                    .compose(RxLifecycleAndroid.bindActivity(getMvpView().getLifecycle()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::showPhotos,
                            this::showError
                    );
        }
    }

    @CheckResult
    private boolean checkNetwork() {
        boolean isConnected = mNetworkUtils.isConnected();
        Timber.d("Network %s available", isConnected ? "is" : "isn't");
        if (!isConnected) {
            getMvpView().showNoInternetConnection();
        }
        return isConnected;
    }

    private void showPhotos(@NonNull List<Photo> photos) {
        Timber.d("Loaded %d photos", photos.size());
        if (photos.isEmpty()) {
            getMvpView().showPhotosEmpty();
        } else {
            getMvpView().showPhotos(photos);
        }
    }

    private void showError(@NonNull Throwable error) {
        Timber.e(error, "There was an error loading the photos");
        getMvpView().showError();
    }

}