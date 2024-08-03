package com.beaconcode.kitchinventory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitInstance {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static volatile Retrofit instance;

    private RetrofitInstance() {
    }

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitInstance.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }
}
