package com.example.cowin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASEURl = "http://cowinbot.ddns.net:8019/";



    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().
                    baseUrl(BASEURl).
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    build();
        }
        return retrofit;
    }
}