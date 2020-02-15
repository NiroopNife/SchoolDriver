package com.marty.track.ForgotPassword.PhaseTwo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OPTService {

    @FormUrlEncoded
    @POST("")
    Call<OTPResponse> sendreceivedOTP(@Field("otp") String str, @Field("password") String str2);
}
