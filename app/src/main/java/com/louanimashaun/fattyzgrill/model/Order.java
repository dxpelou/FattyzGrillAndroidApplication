package com.louanimashaun.fattyzgrill.model;

import java.util.List;

/**
 * Created by louanimashaun on 21/06/2017.
 */

public class Order {

    private String id;

    private List<Meal> orderItems;

    private double totalPrice;

    private boolean isOrderPending;

    public Order(List<Meal> orderItems, double totalPrice, String orderStatus, boolean isOrderPending){
        orderItems = orderItems;
        totalPrice = totalPrice;
        isOrderPending = isOrderPending;
    }
    private Order(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Meal> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Meal> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderPending() {
        return isOrderPending;
    }

    public void setOrderPending(boolean orderPending) {
        isOrderPending = orderPending;
    }

}
