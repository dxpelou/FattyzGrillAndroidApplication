package com.louanimashaun.fattyzgrill.data.source.local;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.List;

/**
 * Created by louanimashaun on 19/06/2017.
 */

public class MealsLocalDataSoure implements DataSource<Meal>{

    private static MealsLocalDataSoure INSTANCE = null;

    public static MealsLocalDataSoure getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MealsLocalDataSoure();
        }
        return INSTANCE;
    }

    private MealsLocalDataSoure(){}

    @Override
    public void loadData(LoadCallBack<Meal> loadCallBack) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(Meal data, CompletionCallback callback) {

    }

    @Override
    public void saveData(List<Meal> data, CompletionCallback callback) {

    }
}
