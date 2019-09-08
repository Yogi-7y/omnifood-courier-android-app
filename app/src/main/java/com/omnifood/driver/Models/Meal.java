package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meal implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("restaurant")
    private int restaurant;
    @SerializedName("meal")
    private String meal;
    @SerializedName("price")
    private String price;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;


    public Meal(String id, int restaurant, String meal, String price, String description, String image) {
        this.id = id;
        this.restaurant = restaurant;
        this.meal = meal;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public String getMeal() {
        return meal;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
