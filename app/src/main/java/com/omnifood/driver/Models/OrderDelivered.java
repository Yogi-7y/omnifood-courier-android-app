package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

public class OrderDelivered {

    @SerializedName("token")
    private String token;
    @SerializedName("order_id")
    private String orderId;

    public OrderDelivered(String token, String orderId) {
        this.token = token;
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public String getOrderId() {
        return orderId;
    }
}
