package com.louanimashaun.fattyzgrill.data;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.LinkedHashMap;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * class is used to access the meals data
 */

public class MealsRepository extends AbstractRepository<Meal> {

    private static MealsRepository INSTANCE;

    public static MealsRepository getInstance(@NonNull DataSource localDatasource, @NonNull DataSource remoteDataSource){
        if (INSTANCE == null){
            INSTANCE = new MealsRepository(localDatasource, remoteDataSource);
        }
        return INSTANCE;
    }

    private MealsRepository(DataSource localDatasource, DataSource remoteDataSource){
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
            mCachedData.put(meal.getLuid(), meal);
        }
    }

    @Override
    protected void putDataItemInCache(Meal data) {
        mCachedData.put(data.getLuid(), data);
    }
}
