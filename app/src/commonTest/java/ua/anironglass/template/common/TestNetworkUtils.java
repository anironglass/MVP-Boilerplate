package ua.anironglass.template.common;

import ua.anironglass.template.utils.NetworkUtils;


public class TestNetworkUtils extends NetworkUtils {

    private boolean mIsConnected;

    public TestNetworkUtils(boolean isConnected) {
        mIsConnected = isConnected;
    }

    public TestNetworkUtils() {
        this(true);
    }

    @Override
    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }

}