package com.louanimashaun.fattyzgrill.model;

import com.google.firebase.database.Exclude;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by louanimashaun on 21/06/2017.
 */


public class Order extends RealmObject {

    @PrimaryKey
    @Exclude
    private String id;

    private RealmList<RealmString> mMealIdsRealm;

    private RealmList<RealmInteger> mQuantitiesRealm;

    @Ignore
    private List<String> mMealIds;

    @Ignore
    private List<Integer> mQuantities;

    private String mUserId;

    private double mTotalPrice;

    private boolean mIsOrderAccepted;

    private String mSenderNotifcationToken;

    public Order(RealmList<RealmString> mealIdsRealm, RealmList<RealmInteger> quantities, double totalPrice, boolean isOrderPending){
        mMealIdsRealm = mealIdsRealm;
        mQuantitiesRealm = quantities;
        mTotalPrice = totalPrice;
        mIsOrderAccepted = isOrderPending;
    }

    public Order(){}

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.mTotalPrice = totalPrice;
    }

    public boolean isOrderAccepted() {
        return mIsOrderAccepted;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        mIsOrderAccepted = orderAccepted;
    }

    public String getSenderNotifcationToken(){
        return mSenderNotifcationToken;
    }

    public void setSenderNotifcationToken(String token){
        mSenderNotifcationToken = token;
    }

    @Exclude
    public List<RealmString> getMealIdsRealm() {
        return mMealIdsRealm;
    }

    public void setMealIdsRealm(RealmList<RealmString> mealIdsRealm) {
        mMealIdsRealm = mealIdsRealm;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public List<String> getMealIds() {
        return mMealIds;
    }

    public void setMealIds(List<String> mealIds) {
        mMealIds = mealIds;
    }

    @Exclude
    public RealmList<RealmInteger> getQuantitiesRealm() {
        return mQuantitiesRealm;
    }

    public void setQuantitiesRealm(RealmList<RealmInteger> quantities) {
        mQuantitiesRealm = quantities;
    }

    public List<Integer> getQuantities() {
        return mQuantities;
    }

    public void setQuantities(List<Integer> quantities) {
        mQuantities = quantities;
    }
}
