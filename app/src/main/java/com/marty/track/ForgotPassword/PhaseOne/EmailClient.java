package com.marty.track.ForgotPassword.PhaseOne;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marty.track.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmailClient {

    private static Retrofit retrofit = null;

    private EmailClient() {
    }

    public static Retrofit forgotFirst() {
        Gson create = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
