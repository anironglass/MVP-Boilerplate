package ua.anironglass.template.utils;

import android.support.annotation.Nullable;

import rx.Subscription;

public final class RxUtils {

    private RxUtils() {
        throw new AssertionError("No instances!");
    }

    public static void unsubscribe(@Nullable Subscription subscription) {
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}