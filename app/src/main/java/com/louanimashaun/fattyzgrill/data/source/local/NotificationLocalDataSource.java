package com.louanimashaun.fattyzgrill.data.source.local;

import android.util.Log;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Notification;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by louanimashaun on 12/09/2017.
 */

@Singleton
public class NotificationLocalDataSource implements DataSource<Notification> {

    private static NotificationLocalDataSource INSTANCE = null;
    private static Realm realm;

    public static NotificationLocalDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NotificationLocalDataSource();
        }

        return INSTANCE;
    }

    @Inject
    public NotificationLocalDataSource(){
        realm = Realm.getDefaultInstance();
        Log.d("realm path: ",realm.getPath());
    }

    @Override
    public void loadData(final LoadCallback<Notification> callback) {
        Realm realm2 = Realm.getDefaultInstance();

        try {
            RealmResults<Notification> result = realm2.where(Notification.class).findAll();
            if (result.size() != 0) {
                callback.onDataLoaded(realm2.copyFromRealm(result.sort("createdAt", Sort.DESCENDING)));
            } else {
                callback.onDataNotAvailable();
            }

        }catch(Exception e){
            callback.onDataNotAvailable();
        }finally {
            realm2.close();
        }

    }

    @Override
    public void getData(final String id, final GetCallback<Notification> callback) {
        Realm realm = Realm.getDefaultInstance();

        try {

                    Notification result = realm.where(Notification.class).equalTo("id", id).findFirst();

                    if (result != null) {
                        callback.onDataLoaded(result);
                    } else {
                        callback.onDataNotAvailable();
                    }

        }finally {
            realm.close();
        }




    }

    @Override
    public void loadDataByIds(List<String> ids, LoadCallback<Notification> callback) {
        //
    }

    @Override
    public void deleteAll() {

    }


    public void saveData(final Notification data, final CompletionCallback callback, Integer i) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        }, new Realm.Transaction.OnSuccess(){

            @Override
            public void onSuccess() {
                if(callback != null) callback.onComplete();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if(callback!= null) callback.onCancel();
            }
        });
    }

    @Override
    public void saveData(final Notification data, final CompletionCallback callback){
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            Notification d = realm.copyToRealmOrUpdate(data);
            realm.commitTransaction();

        }finally {
            realm.close();
        }
    }

    @Override
    public void saveData(List<Notification> data, CompletionCallback callback) {

    }


    public void addNotificationChange(RealmChangeListener<Notification> changeListener){
        //realm.where(Notification.class).findAll().addChangeListener();
    }


}
