package com.marty.track.Login;

import com.marty.track.Constants.Constant;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginClient {

    private static Retrofit retrofit = null;

    private LoginClient() {}

    public static Retrofit driverLogin() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
