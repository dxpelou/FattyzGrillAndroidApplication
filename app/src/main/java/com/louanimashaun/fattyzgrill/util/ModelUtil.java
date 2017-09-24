package com.louanimashaun.fattyzgrill.util;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.RealmInteger;
import com.louanimashaun.fattyzgrill.model.RealmString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by louanimashaun on 15/06/2017.
 */

public class ModelUtil {

    private static int baseId = 0;
    public static final int LIST_SIZE = 3;

    private ModelUtil(){}

    public static List<Meal> createStubMealsList(){
        List<Meal> meals = new ArrayList<>();

        for(int i = 0; i < LIST_SIZE; i++){
            Meal meal = createStubMeal();
            if(i < LIST_SIZE/2){
                meal.setCategory("Chicken");
            }else{
                meal.setCategory("Burger");
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

    public static List<String> createStubMealIDList(){
        List<String> list = new ArrayList<>();
        String id ="000";


        for(int i = 0; i < LIST_SIZE; i++){
            list.add(id + i);
        }

        return list;
    }


    public static Map<String,Integer> createStubMealIdQuantityList(){
        Map<String,Integer> map = new HashMap<>();
        List<String> ids = createStubMealIDList();
        for(String id : ids){
            map.put(id, 1);
        }

        return map;
    }


    public static RealmList<RealmString> toRealmStringList(List<String> stringList){
        int size = stringList.size();
        RealmString[] array = new RealmString[size];
        RealmString realmString = null;

        for(int i =0; i < size; i++ ){
            realmString = new RealmString(stringList.get(i));
            array[i] = realmString;
        }

        return new RealmList<RealmString>(array);
    }


    public static RealmList<RealmInteger> toRealmIntegerList(List<Integer> intList){
        int size = intList.size();
        RealmInteger[] array = new RealmInteger[size];
        RealmInteger realmInteger = null;

        for(int i =0; i< size; i++){
            realmInteger = new RealmInteger(intList.get(i));
            array[i] = realmInteger;
        }

        return new RealmList<RealmInteger>(array);
    }


    public static Map<String,String> convertToTitleIdMap(List<Meal> meals){
        Map<String, String> mealsMap = new HashMap<>();

        for(Meal meal : meals){
            mealsMap.put(meal.getId(), meal.getTitle());
        }

        return mealsMap;
    }
}
