package com.marty.track.SendLocation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendLocationService {

    @FormUrlEncoded
    @POST("location/")
    Call<SendLocationResponse> sendLocation(
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("loc_driver_id") String loc_driver_id,
            @Field("loc_school_id") String loc_school_id
    );
}
