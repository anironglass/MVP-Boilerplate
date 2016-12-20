package ua.anironglass.template.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.template.injection.ApplicationContext;


@Singleton
public final class NetworkUtils {

    private Context mContext;

    @Inject
    public NetworkUtils(@ApplicationContext Context context) {
        mContext = context;
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