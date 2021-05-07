package com.example.cowin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
        @GET("capture_sms/?")
        Call<SendOTP> sendOTP(@Query("sms") String sms,
                                  @Query("number") String number);
}

