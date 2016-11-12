package ua.anironglass.template.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;


public final class NetworkUtils {

    private NetworkUtils() {
        throw new AssertionError("No instances!");
    }

    public static boolean isConnected(@NonNull final Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isAvailable()
                && activeNetwork.isConnectedOrConnecting();
    }

}