package com.louanimashaun.fattyzgrill.data;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.util.ModelUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * class is used to access the meals data
 */

public class MealRepository extends AbstractRepository<Meal> {

    private static MealRepository INSTANCE;

    public static MealRepository getInstance(@NonNull DataSource localDatasource, @NonNull DataSource remoteDataSource){
        if (INSTANCE == null){
            INSTANCE = new MealRepository(localDatasource, remoteDataSource);
        }
        return INSTANCE;
    }

    private MealRepository(DataSource localDatasource, DataSource remoteDataSource){
        mLocalDataSource = checkNotNull(localDatasource);
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    @Override
    protected void refreshCache(List<Meal> data) {
        if(mCachedData == null){
            mCachedData = new LinkedHashMap<>();
        }else {
            mCachedData.clear();
        }

        for(Meal meal : data){
            //mCachedData.put(meal.getLuid(), meal);
        }
    }

    @Override
    protected void putDataItemInCache(Meal data) {
        //mCachedData.put(data.getLuid(), data);
    }

    public void load(LoadCallback callback){
        callback.onDataLoaded(ModelUtil.createStubMealsList());
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }


    /*public void loadMealTitlesWithId(){
        loadData(new LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {

                Map<String, String> mealTitles = new HashMap<String, String>();

                for(Meal meal : data){
                    mealTitles.put(data.)
                }

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }*/


}
