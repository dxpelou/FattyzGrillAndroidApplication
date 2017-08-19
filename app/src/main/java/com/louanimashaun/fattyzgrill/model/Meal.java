package com.louanimashaun.fattyzgrill.model;

/**
 * Created by louanimashaun on 14/06/2017.
 */

public class Meal {


    private String title;

    private double price;

    private String categeory;

    private Meal(){}

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

}
