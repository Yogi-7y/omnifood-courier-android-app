package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PickOrder implements Serializable {

    @SerializedName("token")
    private String token;
    @SerializedName("order_id")
    private String orderId;

    public PickOrder(String token, String orderId) {
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
