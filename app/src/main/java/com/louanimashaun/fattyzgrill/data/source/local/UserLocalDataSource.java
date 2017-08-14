package com.louanimashaun.fattyzgrill.data.source.local;

import android.content.Context;
import android.provider.ContactsContract;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by louanimashaun on 06/08/2017.
 */

public class UserLocalDataSource implements DataSource<User> {

    private static UserLocalDataSource INSTANCE = null;
    private static Context mContext;
    private static Realm realm;

    public static UserLocalDataSource getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new UserLocalDataSource(context);
        }
        return INSTANCE;
    }

    private UserLocalDataSource(Context context){
        mContext = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void loadData(LoadCallback<User> callback) {

    }

    @Override
    public void getData(String id, GetCallback<User> callback) {
        User user = realm.where(User.class).equalTo("id", id).findFirstAsync();

        if(user != null){
            callback.onDataLoaded(user);
        }else{
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void deleteAll() {

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
