package com.marty.track.ForgotPassword.PhaseTwo;

import com.marty.track.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPClient {

    private static Retrofit retrofit = null;

    private OTPClient() {
    }

    public static Retrofit forgotSecond() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
