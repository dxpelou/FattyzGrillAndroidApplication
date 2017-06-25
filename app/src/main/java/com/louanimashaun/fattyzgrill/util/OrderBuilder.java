package com.louanimashaun.fattyzgrill.util;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

/**
 * Created by louanimashaun on 25/06/2017.
 */

public class OrderBuilder {

    public static Order build(List<Meal> meals){
        double totalPrice = 0;

        for(Meal meal : meals){
            totalPrice += meal.getPrice();
        }
        return new Order(meals, totalPrice, true);
    }
}
