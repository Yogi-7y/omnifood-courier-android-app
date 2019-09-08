package com.omnifood.driver.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {

    @SerializedName("token")
    private String token;
    @SerializedName("user_id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;

    public Token(String token, String id, String email, String username, String firstName, String lastName, String address, String phone) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
