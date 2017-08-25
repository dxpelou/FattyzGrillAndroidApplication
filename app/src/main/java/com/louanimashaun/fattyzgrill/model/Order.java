package com.louanimashaun.fattyzgrill.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by louanimashaun on 21/06/2017.
 */

public class Order extends RealmObject {

    private String id;

    private RealmList<Meal> mOrderItems;

    private double mTotalPrice;

    private boolean mIsOrderPending;

    private String mSenderNotifcationToken;

    public Order(RealmList<Meal> orderItems, double totalPrice,  boolean isOrderPending){
        mOrderItems = orderItems;
        mTotalPrice = totalPrice;
        mIsOrderPending = isOrderPending;
    }
    public Order(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Meal> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(RealmList<Meal> orderItems) {
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

    public String getSenderNotifcationToken(){
        return mSenderNotifcationToken;
    }

    public void setSenderNotifcationToken(String token){
        mSenderNotifcationToken = token;
    }

}
