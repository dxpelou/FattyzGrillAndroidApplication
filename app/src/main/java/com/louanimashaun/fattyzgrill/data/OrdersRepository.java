package com.louanimashaun.fattyzgrill.data;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.model.Order;

import java.util.LinkedHashMap;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 24/06/2017.
 */

public class OrdersRepository extends AbstractRepository<Order> {

    private static OrdersRepository INSTANCE;

    public static OrdersRepository getInstance(@NonNull DataSource localDatasource, @NonNull DataSource remoteDataSource){
        if (INSTANCE == null){
            INSTANCE = new OrdersRepository(localDatasource, remoteDataSource);
        }
        return INSTANCE;
    }

    private OrdersRepository(DataSource localDatasource, DataSource remoteDataSource){
        mLocalDataSource = checkNotNull(localDatasource);
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    @Override
    protected void refreshCache(List<Order> data) {
        if(mCachedData == null){
            mCachedData = new LinkedHashMap<>();
        }else {
            mCachedData.clear();
        }
        for(Order order : data){
            putDataItemInCache(order);
        }
    }

    @Override
    protected void putDataItemInCache(Order data) {
        checkNotNull(data);
        if (mCachedData == null){
            mCachedData = new LinkedHashMap<>();
        }
        //mCachedData.put(data.getId(), data);
    }

}
