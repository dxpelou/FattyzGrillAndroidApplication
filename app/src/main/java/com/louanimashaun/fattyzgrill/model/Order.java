package com.louanimashaun.fattyzgrill.model;

import java.util.List;

/**
 * Created by louanimashaun on 21/06/2017.
 */

public class Order {

    private String id;

    private List<Meal> mOrderItems;

    private double mTotalPrice;

    private boolean mIsOrderPending;

    public Order(List<Meal> orderItems, double totalPrice,  boolean isOrderPending){
        mOrderItems = orderItems;
        mTotalPrice = totalPrice;
        mIsOrderPending = isOrderPending;
    }
    private Order(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Meal> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(List<Meal> orderItems) {
        this.mOrderItems = orderItems;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.mTotalPrice = totalPrice;
    }

    public boolean isOrderPending() {
        return mIsOrderPending;
    }

    public void setOrderPending(boolean orderPending) {
        mIsOrderPending = orderPending;
    }

}
