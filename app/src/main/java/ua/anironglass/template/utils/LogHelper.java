package ua.anironglass.template.utils;

import android.support.annotation.NonNull;

public final class LogHelper {

    private LogHelper() {
        throw new AssertionError("No instances!");
    }

    @NonNull
    public static String attachThreadName(@NonNull String message) {
        return Thread.currentThread().getName() + ": " + message;
    }

}