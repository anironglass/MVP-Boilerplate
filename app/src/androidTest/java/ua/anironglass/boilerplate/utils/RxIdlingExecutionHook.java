package ua.anironglass.boilerplate.utils;

import android.support.annotation.NonNull;

import rx.plugins.RxJavaHooks;

/**
 * RxJava Observable execution hook that handles updating the active subscription
 * count for a given Espresso RxIdlingResource.
 */
public final class RxIdlingExecutionHook {

    private RxIdlingResource mRxIdlingResource;

    public RxIdlingExecutionHook(@NonNull RxIdlingResource rxIdlingResource) {
        mRxIdlingResource = rxIdlingResource;
    }

    public void setHook() {
        RxJavaHooks.reset();
        RxJavaHooks.setOnObservableStart((observable, onSubscribe) -> {
            mRxIdlingResource.incrementActiveSubscriptionsCount();
            return onSubscribe;
        });
        RxJavaHooks.setOnObservableSubscribeError(throwable -> {
            mRxIdlingResource.decrementActiveSubscriptionsCount();
            return throwable;
        });
        RxJavaHooks.setOnObservableReturn(subscription -> {
            mRxIdlingResource.decrementActiveSubscriptionsCount();
            return subscription;
        });
    }

    public void removeHook() {
        RxJavaHooks.clear();
        RxJavaHooks.reset();
    }

}