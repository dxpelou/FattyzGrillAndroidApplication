package com.louanimashaun.fattyzgrill.data;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.di.Local;
import com.louanimashaun.fattyzgrill.di.Remote;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 24/06/2017.
 */

public class OrderRepository extends AbstractRepository<Order> {

    private static OrderRepository INSTANCE;

    public static OrderRepository getInstance(@NonNull DataSource localDatasource, @NonNull DataSource remoteDataSource){
        if (INSTANCE == null){
            INSTANCE = new OrderRepository(localDatasource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Inject
    public OrderRepository(@Local DataSource localDatasource, @Remote DataSource remoteDataSource){
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

    @Override
    public void getData(String id, GetCallback<Order> callback) {

    }
}
