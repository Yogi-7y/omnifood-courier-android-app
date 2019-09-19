package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Consumer implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public Consumer(String id, String name, String phone, String address, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
