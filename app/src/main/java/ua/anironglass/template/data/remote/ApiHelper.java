package ua.anironglass.template.data.remote;


import javax.inject.Inject;
import javax.inject.Singleton;

import ua.anironglass.template.injection.ApiServiceInstance;

@Singleton
public class ApiHelper {

    private static final int RETRY_COUNT_FOR_REQUEST = 3;
    private static final int TIMEOUT_IN_SECONDS = 15;

    private ApiService mApiService;

    @Inject
    @SuppressWarnings("WeakerAccess")  // Used in global singleton
    public ApiHelper(@ApiServiceInstance ApiService apiService) {
        mApiService = apiService;
    }

}