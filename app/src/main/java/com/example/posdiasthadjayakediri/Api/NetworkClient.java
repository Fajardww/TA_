package com.example.posdiasthadjayakediri.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL = "http://192.168.18.2:8080/dbpos/";
//    private static final String BASE_URL = "https://diasthadjayakediri.000webhostapp.com/";

    //================sharedpreferences=================//
    public static final String KEY_ISE_LOGGED_IN = "isLoggedIn";
    public static final String PREFERENCE_NAME = "session";
    public static final String KEY_LEVEL = "level";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}