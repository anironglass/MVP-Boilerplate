package ua.anironglass.template.runner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;

import ua.anironglass.template.utils.RxIdlingExecutionHook;
import ua.anironglass.template.utils.RxIdlingResource;


/**
 * Runner that registers a Espresso Indling resource that handles waiting for
 * RxJava Observables to finish.
 */
public final class RxAndroidJUnitRunner extends UnlockDeviceAndroidJUnitRunner {

    private RxIdlingExecutionHook mRxIdlingExecutionHook;

    @Override
    public void onCreate(@NonNull Bundle arguments) {
        super.onCreate(arguments);
        RxIdlingResource rxIdlingResource = new RxIdlingResource();
        mRxIdlingExecutionHook = new RxIdlingExecutionHook(rxIdlingResource);
        mRxIdlingExecutionHook.setHook();
        Espresso.registerIdlingResources(rxIdlingResource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxIdlingExecutionHook.removeHook();
    }
}