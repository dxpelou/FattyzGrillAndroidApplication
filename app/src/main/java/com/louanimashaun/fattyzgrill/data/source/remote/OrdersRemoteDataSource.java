package com.louanimashaun.fattyzgrill.data.source.remote;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

/**
 * Created by louanimashaun on 24/06/2017.
 */

public class OrdersRemoteDataSource implements DataSource<Order> {

    private static OrdersRemoteDataSource INSTANCE = null;
    private final FirebaseDatabase mFirebaseDatabase ;
    private DatabaseReference mOrdersReference;
    private static final String ORDERS_PATH= "orders";

    public static OrdersRemoteDataSource getInstance(){
        if (INSTANCE == null){
            INSTANCE = new OrdersRemoteDataSource();
        }
        return INSTANCE;
    }

    private OrdersRemoteDataSource(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrdersReference = mFirebaseDatabase.getReference(ORDERS_PATH);
    }

    @Override
    public void loadData(LoadCallBack<Order> loadCallBack) {
        //not in use
    }

    @Override
    public void deleteAll() {
        // not in use
    }

    @Override
    public void saveData(Order data, final CompletionCallback callback) {
        mOrdersReference.push().setValue(data, new DatabaseReference.CompletionListener(){

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    callback.onComplete();
                    return;
                }

                callback.onCancel();
            }
        });
    }

    @Override
    public void saveData(List<Order> data, final CompletionCallback callback) {
        for(int i = 0; i < data.size(); i++){
            if(i < data.size() - 1) {
                mOrdersReference.push().setValue(data.get(i));
            }
            else{
                mOrdersReference.push().setValue(data.get(i), new DatabaseReference.CompletionListener(){
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            callback.onComplete();
                            return;
                        }

                        callback.onCancel();
                    }
                });
            }
        }
    }
}
