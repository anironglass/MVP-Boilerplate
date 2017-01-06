package ua.anironglass.boilerplate.utils;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public final class TestLifecycleFactory {

    private static final BehaviorSubject<ActivityEvent> mLifecycleSubject =
            BehaviorSubject.create();

    @NonNull
    public static Observable<ActivityEvent> getLifecycle() {
        return mLifecycleSubject.asObservable();
    }

}