package com.louanimashaun.fattyzgrill.data.source.local;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by louanimashaun on 25/06/2017.
 */
@Singleton
public class OrdersLocalDataSource implements DataSource<Order>{

    private static OrdersLocalDataSource INSTANCE = null;

    private static Realm realm;

    public static OrdersLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrdersLocalDataSource();
        }
        return INSTANCE;
    }

    @Inject
    public OrdersLocalDataSource(){
    }

    @Override
    public void loadData(LoadCallback<Order> loadCallback) {

    }

    @Override
    public void loadDataByIds(List<String> ids, LoadCallback<Order> callback) {

    }

    @Override
    public void getData(final String id, final GetCallback getCallback) {
        Realm realm = Realm.getDefaultInstance();
        try {
                    RealmResults result = realm.where(Order.class).equalTo("id", id).findAll();

                    if (result.size() == 0) {
                        getCallback.onDataNotAvailable();
                    } else {
                        getCallback.onDataLoaded(result.first());
                    }

        }finally {
            realm.close();
        }



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
