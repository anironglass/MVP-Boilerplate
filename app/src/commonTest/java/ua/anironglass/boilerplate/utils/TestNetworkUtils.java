package ua.anironglass.boilerplate.utils;


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