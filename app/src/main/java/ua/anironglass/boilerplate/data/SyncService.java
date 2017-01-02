package ua.anironglass.boilerplate.data;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;
import ua.anironglass.boilerplate.App;
import ua.anironglass.boilerplate.utils.NetworkStateHelper;
import ua.anironglass.boilerplate.utils.NetworkUtils;
import ua.anironglass.boilerplate.utils.RxUtils;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    @Inject NetworkUtils mNetworkUtils;
    @Inject NetworkStateHelper mNetworkStateHelper;
    private Subscription mSyncSubscription;
    private Subscription mWaitingForNetworkSubscription;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, final int startId) {
        Timber.d("Starting sync...");

        if (!mNetworkUtils.isConnected()) {
            waitForNetwork(startId);
        } else {
            syncPhotos(startId);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(mSyncSubscription);
        RxUtils.unsubscribe(mWaitingForNetworkSubscription);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void waitForNetwork(final int startId) {
        Timber.d("Sync canceled: connection not available. Waiting for network...");
        RxUtils.unsubscribe(mWaitingForNetworkSubscription);
        mWaitingForNetworkSubscription = mNetworkStateHelper.handleChanges()
                .subscribe(
                        isConnected -> {
                            if (isConnected) {
                                Timber.d("Connection is available now!");
                                syncPhotos(startId);
                                RxUtils.unsubscribe(mWaitingForNetworkSubscription);
                            }
                        },
                        error -> {
                            Timber.w(error, "Error syncing.");
                            stopSelf(startId);
                        });
    }

    private void syncPhotos(final int startId) {
        Timber.d("Sync photos...");
        RxUtils.unsubscribe(mSyncSubscription);
        mSyncSubscription = mDataManager.syncPhotos()
                .subscribe(
                        photos -> { },
                        error -> {
                            Timber.w(error, "Error syncing.");
                            stopSelf(startId);
                        },
                        () -> {
                            Timber.d("Synced successfully!");
                            stopSelf(startId);
                        });
    }

}