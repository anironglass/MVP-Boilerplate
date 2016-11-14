package ua.anironglass.template.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.anironglass.template.utils.AutoValueGsonTypeAdapterFactory;


public interface ApiService {

    class Builder {

        private static final String BASE_URL = "...";
        private static final int CONNECT_TIMEOUT = 15;
        private static final int WRITE_TIMEOUT = 20;
        private static final int READ_TIMEOUT = 20;

        private Builder() {
            throw new AssertionError("No instances!");
        }

        @NonNull
        public static ApiService newApiService() {
            return getRetrofit()
                    .create(ApiService.class);
        }

        @NonNull
        private static Retrofit getRetrofit() {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(AutoValueGsonTypeAdapterFactory.create())
                    .create();
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

    }

}