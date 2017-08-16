package com.louanimashaun.fattyzgrill.data.source.local;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by louanimashaun on 06/08/2017.
 */

public class UserLocalDataSource implements DataSource<User> {

    private static UserLocalDataSource INSTANCE = null;
    private static Context mContext;
    private static Realm realm;

    private static final String TAG = "UserLocalDataSource";

    public static UserLocalDataSource getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new UserLocalDataSource(context);
        }
        return INSTANCE;
    }

    private UserLocalDataSource(Context context){
        mContext = context;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        realm = Realm.getInstance(config);

        Log.d(TAG, realm.getPath());
    }

    @Override
    public void loadData(LoadCallback<User> callback) {

    }

    @Override
    public void getData(String id, GetCallback<User> callback) {
        RealmResults<User> result = realm.where(User.class).equalTo("userId", id).findAllAsync();

        if(result.size() == 0){
            callback.onDataNotAvailable();
        }else{
            callback.onDataLoaded(result.first());
        }
    }

    @Override
    public void deleteAll() {
        final RealmResults<User> allUsers = realm.where(User.class).findAll();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                allUsers.deleteAllFromRealm();
            }
        });

    }

    @Override
    public void saveData(final User data, final CompletionCallback callback) {
       realm.executeTransactionAsync(new Realm.Transaction(){

           @Override
           public void execute(Realm realm) {
                realm.copyToRealm(data);
           }
       }, new Realm.Transaction.OnSuccess(){
           @Override
           public void onSuccess() {
                 callback.onComplete();
           }
       }, new Realm.Transaction.OnError(){
           @Override
           public void onError(Throwable error) {
                callback.onCancel();
           }
       });

    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {

    }
}
