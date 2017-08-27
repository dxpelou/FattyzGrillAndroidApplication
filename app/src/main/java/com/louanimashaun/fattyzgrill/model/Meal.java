package com.louanimashaun.fattyzgrill.model;

import com.google.firebase.database.Exclude;

import io.realm.RealmObject;

/**
 * Created by louanimashaun on 14/06/2017.
 */

public class Meal extends RealmObject {

    private String title;

    private double price;

    private String categeory;

    @Exclude
    private boolean isCheckedOut;

    public Meal(){}

    public Meal( String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory(){
        return categeory;
    }

    public void setCategeory(String category){
        this.categeory = category;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }
}
