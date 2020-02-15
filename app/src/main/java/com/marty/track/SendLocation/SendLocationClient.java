package com.marty.track.SendLocation;

import com.marty.track.Constants.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendLocationClient {

    private static Retrofit retrofit = null;

    private SendLocationClient(){}

    public static Retrofit sendLocation(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
