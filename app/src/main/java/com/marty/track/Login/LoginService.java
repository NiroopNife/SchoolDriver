package com.marty.track.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("driverlogin/")
    Call<LoginResponse> driverLogin(
            @Field("phone_num") String str,
            @Field("password") String str2,
            @Field("school_id") String str3
    );
}
