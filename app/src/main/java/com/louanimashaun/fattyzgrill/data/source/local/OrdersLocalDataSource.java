package com.louanimashaun.fattyzgrill.data.source.local;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

import io.realm.Realm;

/**
 * Created by louanimashaun on 25/06/2017.
 */

public class OrdersLocalDataSource implements DataSource<Order>{

    private static OrdersLocalDataSource INSTANCE = null;

    private static Realm realm;

    public static OrdersLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrdersLocalDataSource();
        }
        return INSTANCE;
    }

    private OrdersLocalDataSource(){
    }

    @Override
    public void loadData(LoadCallback<Order> loadCallback) {

    }

    @Override
    public void getData(String id, GetCallback getCallback) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(Order data, DataSource.CompletionCallback callback) {

    }

    @Override
    public void saveData(List<Order> data, DataSource.CompletionCallback callback) {

    }
}
