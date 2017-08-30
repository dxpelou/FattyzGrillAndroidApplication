package com.louanimashaun.fattyzgrill.model;

import com.google.firebase.database.Exclude;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by louanimashaun on 14/06/2017.
 */

public class Meal extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private double price;

    private String category;


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
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
