package com.louanimashaun.fattyzgrill.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.List;

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

    private RealmList<RealmString> mealIdsRealm;

    private RealmList<RealmInteger> quantitiesRealm;

    @Ignore
    private List<String> mealIds;

    @Ignore
    private List<Integer> quantities;

    private String userId;

    private double totalPrice;

    private boolean isOrderAccepted;

    private String senderNotificationToken;

    private Date createdAt;

    private Date acceptedAt;

    private Date collectionAt;

    public Order(RealmList<RealmString> mealIdsRealm, RealmList<RealmInteger> quantities, double totalPrice, boolean isOrderPending){
        this.mealIdsRealm = mealIdsRealm;
        quantitiesRealm = quantities;
        this.totalPrice = totalPrice;
        isOrderAccepted = isOrderPending;
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
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderAccepted() {
        return isOrderAccepted;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        isOrderAccepted = orderAccepted;
    }

    public String getSenderNotificationToken(){
        return senderNotificationToken;
    }

    public void setSenderNotificationToken(String token){
        senderNotificationToken = token;
    }

    @Exclude
    public RealmList<RealmString> getMealIdsRealm() {
        return mealIdsRealm;
    }

    public void setMealIdsRealm(RealmList<RealmString> mealIdsRealm) {
        this.mealIdsRealm = mealIdsRealm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getMealIds() {
        return mealIds;
    }

    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }

    @Exclude
    public RealmList<RealmInteger> getQuantitiesRealm() {
        return quantitiesRealm;
    }

    public void setQuantitiesRealm(RealmList<RealmInteger> quantities) {
        quantitiesRealm = quantities;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Date getCollectionAt() {
        return collectionAt;
    }

    public void setCollectionAt(Date collectionAt) {
        this.collectionAt = collectionAt;
    }
}
