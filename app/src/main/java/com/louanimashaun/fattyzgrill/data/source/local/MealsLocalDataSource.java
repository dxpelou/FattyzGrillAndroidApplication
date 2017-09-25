package com.louanimashaun.fattyzgrill.data.source.local;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 19/06/2017.
 */
@Singleton
public class MealsLocalDataSource implements DataSource<Meal>{

    private static MealsLocalDataSource INSTANCE = null;
    private static Realm realm;


    public static MealsLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MealsLocalDataSource();
        }
        return INSTANCE;
    }

    @Inject
    public MealsLocalDataSource(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadData(LoadCallback<Meal> callback) {
        RealmResults<Meal> result = realm.where(Meal.class).findAllAsync();

        if(result.size() == 0){
            callback.onDataNotAvailable();
        }else{
            callback.onDataLoaded(realm.copyFromRealm(result));
        }
    }

    @Override
    public void loadDataByIds(List<String> ids, LoadCallback<Meal> callback) {
        checkNotNull(callback);

        List<Meal>  results = new ArrayList<>();

        for(String id : ids){
            Meal meal = realm.where(Meal.class).equalTo("id", id).findFirst();
            if(meal == null) callback.onDataNotAvailable();
            results.add(meal);
        }

        callback.onDataLoaded(results);
    }

    @Override
    public void getData(String id, GetCallback callback) {

        //TODO test using findFirst instead of findAll
        RealmResults<Meal> result = realm.where(Meal.class).equalTo("id", id).findAll();

        if(result.size() == 0){
            callback.onDataNotAvailable();
        }else{
            callback.onDataLoaded(result.first());
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(final Meal data, final CompletionCallback callback) {
        realm.executeTransactionAsync(new Realm.Transaction(){

            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
            }
        }, new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess() {
                if(callback != null) callback.onComplete();

            }
        }, new Realm.Transaction.OnError(){
            @Override
            public void onError(Throwable error) {
                if(callback != null) callback.onCancel();
            }
        });
    }

    @Override
    public void saveData(final List<Meal> data, final CompletionCallback callback) {
        realm.executeTransactionAsync(new Realm.Transaction(){

            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
            }
        }, new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess() {
                if(callback != null) callback.onComplete();

            }
        }, new Realm.Transaction.OnError(){
            @Override
            public void onError(Throwable error) {
                if(callback != null) callback.onCancel();
            }
        });
    }
}
