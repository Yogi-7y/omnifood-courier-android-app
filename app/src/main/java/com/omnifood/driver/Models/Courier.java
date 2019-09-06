package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

public class Courier {

    @SerializedName("user")
    private int id;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;

    public Courier(int id, String phone, String address) {
        this.id = id;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
