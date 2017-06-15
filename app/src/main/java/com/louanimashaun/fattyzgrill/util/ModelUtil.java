package com.louanimashaun.fattyzgrill.util;

import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louanimashaun on 15/06/2017.
 */

public class ModelUtil {

    private static int id = 0;
    private static final int LIST_SIZE = 5;

    private ModelUtil(){}

    public static List<Meal> createStubMealsList(){
        List<Meal> meal = new ArrayList<>();

        for(int i = 0; i < LIST_SIZE; i++){
            meal.add(creatStubMeal());
        }
        return meal;
    }

    public static Meal creatStubMeal(){
        return new Meal(id++, "0", 0.0);
    }
}
