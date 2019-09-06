package com.omnifood.driver;

import com.google.gson.annotations.SerializedName;
import com.omnifood.driver.Models.Courier;
import com.omnifood.driver.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OmnifoodApi {

    @POST("accounts/profile/")
    Call<User> registerUser(@Body User user);

    @POST("accounts/courier/")
    Call<Courier> completeProfile(@Body Courier courier);

}
