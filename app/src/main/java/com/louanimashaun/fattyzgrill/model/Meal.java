package com.louanimashaun.fattyzgrill.model;

/**
 * Created by louanimashaun on 14/06/2017.
 */

public class Meal {

    private static int id;

    private String title;

    private double price;

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

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Meal.id = id;
    }

    public Meal(int id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }
}
