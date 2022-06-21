package com.svalero.toplaptop.api;

import static com.svalero.toplaptop.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToplaptopApi {

    public static ToplaptopApiInterface buildInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ToplaptopApiInterface.class);
    }
}