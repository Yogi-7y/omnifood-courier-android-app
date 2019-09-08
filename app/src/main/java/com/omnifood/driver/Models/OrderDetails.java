package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetails implements Serializable {

    @SerializedName("id")
    private String orderId;
    @SerializedName("meal")
    private String mealId;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("sub_total")
    private String subtotal;

    public OrderDetails(String orderId, String mealId, String quantity, String subtotal) {
        this.orderId = orderId;
        this.mealId = mealId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMealId() {
        return mealId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSubtotal() {
        return subtotal;
    }
}
