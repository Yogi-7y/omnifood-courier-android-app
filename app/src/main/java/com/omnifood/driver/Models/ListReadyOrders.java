package com.omnifood.driver.Models;

import java.io.Serializable;
import java.util.List;

public class ListReadyOrders implements Serializable {

    private String id;
    private Consumer consumer;
    private Restaurant restaurant;
    private Courier courier;
    private List<OrderDetails> orderDetails;
    private String total;
    private String status;
    private String address;
    private Meal meal;

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, Courier courier, List<OrderDetails> orderDetails, String total, String status, String address, Meal meal) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.courier = courier;
        this.orderDetails = orderDetails;
        this.total = total;
        this.status = status;
        this.address = address;
        this.meal = meal;
    }

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, String total, String status, String address) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.total = total;
        this.status = status;
        this.address = address;
    }

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, String total, String status, String address, Meal meal) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.total = total;
        this.status = status;
        this.address = address;
        this.meal = meal;
    }

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, Courier courier, String total, String status) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.courier = courier;
        this.total = total;
        this.status = status;
    }

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, String total, String status, Meal meal) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.total = total;
        this.status = status;
        this.meal = meal;
    }

    public ListReadyOrders(String id, Consumer consumer, Restaurant restaurant, Courier courier, String total, String status, Meal meal) {
        this.id = id;
        this.consumer = consumer;
        this.restaurant = restaurant;
        this.courier = courier;
        this.total = total;
        this.status = status;
        this.meal = meal;
    }

    public String getId() {
        return id;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Courier getCourier() {
        return courier;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public Meal getMeal() {
        return meal;
    }
}
