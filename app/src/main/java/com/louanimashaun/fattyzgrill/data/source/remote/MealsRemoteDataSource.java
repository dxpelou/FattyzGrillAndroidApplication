package com.louanimashaun.fattyzgrill.data.source.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.data.DataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by louanimashaun on 18/06/2017.
 */

@Singleton
public class MealsRemoteDataSource implements DataSource<Meal> {

    private static MealsRemoteDataSource INSTANCE = null;
    private final FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMealsReference;
    private static final String MEALS_PATH= "meals";

    public static MealsRemoteDataSource getInstance(){
        if (INSTANCE == null){
            INSTANCE = new MealsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Inject
    public MealsRemoteDataSource(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMealsReference = mFirebaseDatabase.getReference(MEALS_PATH);
    }

    @Override
    public void loadData(final LoadCallback<Meal> callBack) {
       final List<Meal> meals = new ArrayList<>();

        mMealsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    Meal meal = snapshot.getValue(Meal.class);
                    if(meal != null) {
                        meal.setId(key);
                    }
                    //meal.setLuid(key);
                    meals.add(meal);
                }
                callBack.onDataLoaded(meals);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onDataNotAvailable();
            }
        });

        return;
    }


    //TODO TEST
    @Override
    public void loadDataByIds(List<String> ids, final LoadCallback<Meal> callback) {
        final List<Meal> results = new ArrayList<>();
        for(String id : ids){
            getData(id, new GetCallback<Meal>() {
                @Override
                public void onDataLoaded(Meal data) {
                    results.add(data);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void getData(String id, final GetCallback<Meal> callback) {
        mMealsReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meal meal = dataSnapshot.getValue(Meal.class);
                if(meal == null){
                    callback.onDataNotAvailable();
                    return;
                }

                callback.onDataLoaded(meal);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAll() {
        //not in use
    }

    @Override
    public void saveData(Meal data,CompletionCallback callback) {
        mMealsReference.push().setValue(data);
    }

    @Override
    public void saveData(List<Meal> data, CompletionCallback callback) {
        //not in use§
    }


}
