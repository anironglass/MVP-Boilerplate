package ua.anironglass.boilerplate.utils;

import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class TestLifecycleFactory {

    private static final BehaviorSubject<ActivityEvent> mLifecycleSubject =
            BehaviorSubject.create();

    public static Observable<ActivityEvent> getLifecycle() {
        return mLifecycleSubject.asObservable();
    }

}