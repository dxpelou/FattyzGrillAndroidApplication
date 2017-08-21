package com.louanimashaun.fattyzgrill.util;

import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by louanimashaun on 15/06/2017.
 */

public class ModelUtil {

    private static int id = 0;
    private static final int LIST_SIZE = 3;

    private ModelUtil(){}

    public static List<Meal> createStubMealsList(){
        List<Meal> meals = new ArrayList<>();

        for(int i = 0; i < LIST_SIZE; i++){
            Meal meal = createStubMeal();
            if(i < LIST_SIZE/2){
                meal.setCategeory("Chicken");
            }else{
                meal.setCategeory("Burger");
            }
            meals.add(meal);
        }
        return meals;
    }

    public static Meal createStubMeal(){
        return new Meal( "2 piece chicken and chips", 3.0);
    }

    public static Meal[] toArray(List<Meal> meals){
        Meal[] mealsArray = new Meal[meals.size()];

        for (int i = 0; i < meals.size(); i++){
            mealsArray[i] = meals.get(i);
        }

        return mealsArray;
    }

    public static List<Meal> sortMealsByCategory(List<Meal> meals){
        HashMap mealsByCategory = new HashMap<String, List<Meal>>();

        for (Meal meal : meals){
            String category = meal.getCategory();
            if(mealsByCategory.containsKey(category)){
                ((List<Meal>) mealsByCategory.get(category)).add(meal);
            }else {
                mealsByCategory.put(meal.getCategory(), Arrays.asList(meal));
            }
        }
        return new ArrayList<>(mealsByCategory.values());

    }
}
