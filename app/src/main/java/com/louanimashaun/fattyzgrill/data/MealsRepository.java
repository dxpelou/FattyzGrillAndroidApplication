package com.louanimashaun.fattyzgrill.data;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * class is used to access the meals data
 */

public class MealsRepository {

    private static MealsRepository INSTANCE;

    public static MealsRepository getInstance(){
        if (INSTANCE == null){
            INSTANCE = new MealsRepository();
        }
        return INSTANCE;
    }

    private MealsRepository(){

    }

}
