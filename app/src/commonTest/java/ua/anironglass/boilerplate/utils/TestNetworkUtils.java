package ua.anironglass.boilerplate.utils;


public final class TestNetworkUtils extends NetworkUtils {

    private boolean mIsConnected;

    public TestNetworkUtils() {
        mIsConnected = true;
    }

    @Override
    public boolean isConnected() {
        return mIsConnected;
    }

    public void setConnected(boolean connected) {
        mIsConnected = connected;
    }

}