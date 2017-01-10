package ua.anironglass.boilerplate.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;
import ua.anironglass.boilerplate.injection.qualifiers.ApplicationContext;

@Singleton
public final class NetworkStateHelper {

    private final Context mContext;
    private final IntentFilter mIntentFilter;
    private final NetworkUtils mNetworkUtils;

    @Inject
    public NetworkStateHelper(@ApplicationContext Context context, NetworkUtils networkUtils) {
        mContext = context;
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkUtils = networkUtils;
    }

    @NonNull
    public Observable<Boolean> handleChanges() {
        return Observable.create(new NetworkStateObservable());
    }


    private final class NetworkStateObservable implements Observable.OnSubscribe<Boolean> {

        private BroadcastReceiver mBroadcastReceiver;

        @Override
        public final void call(final Subscriber<? super Boolean> subscriber) {
            mBroadcastReceiver = new NetworkStateReceiver(subscriber);
            mContext.registerReceiver(mBroadcastReceiver, mIntentFilter);
            final Subscription subscription = createSubscription();
            subscriber.add(subscription);
        }

        @NonNull
        private Subscription createSubscription() {
            return Subscriptions.create(() -> mContext.unregisterReceiver(mBroadcastReceiver));
        }

    }

    private final class NetworkStateReceiver extends BroadcastReceiver {

        private final Subscriber<? super Boolean> mSubscriber;
        private boolean mLastNetworkState;

        NetworkStateReceiver(final Subscriber<? super Boolean> subscriber) {
            mSubscriber = subscriber;
            mLastNetworkState = mNetworkUtils.isConnected();
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (!mSubscriber.isUnsubscribed()) {
                boolean isNetworkAvailable = mNetworkUtils.isConnected();
                if (mLastNetworkState ^ isNetworkAvailable) {
                    mSubscriber.onNext(isNetworkAvailable);
                    mLastNetworkState = isNetworkAvailable;
                }
            }
        }

    }

}