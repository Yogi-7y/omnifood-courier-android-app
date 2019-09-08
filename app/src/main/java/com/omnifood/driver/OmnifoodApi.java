package com.omnifood.driver;

import com.omnifood.driver.Models.Courier;
import com.omnifood.driver.Models.ListReadyOrders;
import com.omnifood.driver.Models.Login;
import com.omnifood.driver.Models.PickOrder;
import com.omnifood.driver.Models.Status;
import com.omnifood.driver.Models.Token;
import com.omnifood.driver.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OmnifoodApi {

    @POST("accounts/profile/")
    Call<User> registerUser(@Body User user);

    @POST("accounts/courier/")
    Call<Courier> completeProfile(@Body Courier courier);

    @GET("restaurant/orders/")
    Call<List<ListReadyOrders>> getReadyOrders();

    @POST("accounts/api/login/courier")
    Call<Token> loginUser(@Body Login login);

    @FormUrlEncoded
    @POST("restaurant/order/pick/")
    Call<Status> pickUpOrder(
            @Field("token") String token,
            @Field("order_id") String orderId
    );

}
