package com.louanimashaun.fattyzgrill.data.source.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by louanimashaun on 24/06/2017.
 */
@Singleton
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

    @Inject
    public OrdersRemoteDataSource(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrdersReference = mFirebaseDatabase.getReference(ORDERS_PATH);
    }

    @Override
    public void loadData(LoadCallback<Order> loadCallback) {
        //not in use
    }

    @Override
    public void loadDataByIds(final List<String> ids, final LoadCallback<Order> callback) {
        final List<Order> results = new ArrayList<>();
        for(String id : ids){
            getData(id, new GetCallback<Order>() {
                @Override
                public void onDataLoaded(Order data) {
                    results.add(data);

                    if(results.size() == ids.size()){
                        callback.onDataLoaded(results);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
        if(results.size() == 0){
            callback.onDataNotAvailable();
            return;
        }
    }

    @Override
    public void getData(final String id, final GetCallback getCallback) {
        mOrdersReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order == null){
                    getCallback.onDataNotAvailable();
                    mOrdersReference.child(id).removeEventListener(this);
                    return;
                }
                order.setId(id);
                getCallback.onDataLoaded(order);
                mOrdersReference.child(id).removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getCallback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAll() {
        // not in use
    }

    @Override
    public void saveData(Order data, final CompletionCallback callback) {
        String id = data.getId();
        mOrdersReference.child(id).setValue(data, new DatabaseReference.CompletionListener(){

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    if(callback !=  null) {
                        callback.onComplete();
                    }

                }else{
                    if(callback != null)
                    callback.onCancel();
                }
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
