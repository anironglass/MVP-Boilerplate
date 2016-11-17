package ua.anironglass.template.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import ua.anironglass.template.App;
import ua.anironglass.template.utils.AndroidComponentUtil;
import ua.anironglass.template.utils.NetworkUtils;
import ua.anironglass.template.utils.RxUtils;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    private Subscription mSubscription;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(@NonNull Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (!NetworkUtils.isConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtils.unsubscribe(mSubscription);
        mSubscription = mDataManager.syncPhotos()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        photo -> { },
                        error -> {
                            Timber.w(error, "Error syncing.");
                            stopSelf(startId);
                        },
                        () -> {
                            Timber.d("Synced successfully!");
                            stopSelf(startId);
                        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        RxUtils.unsubscribe(mSubscription);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtils.isConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}