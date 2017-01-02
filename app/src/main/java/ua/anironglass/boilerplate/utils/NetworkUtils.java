package ua.anironglass.boilerplate.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.boilerplate.injection.ApplicationContext;


@Singleton
public class NetworkUtils {

    private Context mContext;

    @Inject
    public NetworkUtils(@ApplicationContext Context context) {
        mContext = context;
    }

    protected NetworkUtils() {
    }

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isAvailable()
                && activeNetwork.isConnectedOrConnecting();
    }

}