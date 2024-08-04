package com.beaconcode.kitchinventory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and managing a Retrofit instance.
 * This class provides a method to get a Retrofit instance configured with a base URL and a logging interceptor.
 */
public abstract class RetrofitClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static volatile Retrofit instance;

    /**
     * Private constructor to prevent instantiation.
     */
    private RetrofitClient() {
    }

    /**
     * Returns the singleton Retrofit instance.
     * If the instance is null, it initializes it with the base URL and a logging interceptor.
     * @return The singleton Retrofit instance.
     */
    public static Retrofit getInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }
        return instance;
    }
}
