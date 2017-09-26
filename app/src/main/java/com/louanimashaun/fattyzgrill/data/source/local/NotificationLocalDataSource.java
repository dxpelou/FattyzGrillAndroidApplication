package com.louanimashaun.fattyzgrill.data.source.local;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.Notification;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
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
    }

    @Override
    public void loadData(LoadCallback<Notification> callback) {
        RealmResults<Notification> result = realm.where(Notification.class).findAllAsync();

        if(result.size() == 0 ){
            callback.onDataLoaded(realm.copyFromRealm(result.sort("updatedAt")));
        }else{
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void getData(String id, GetCallback<Notification> callback) {
        Notification result = realm.where(Notification.class).equalTo("id", id).findFirstAsync();

        if(result != null){
            callback.onDataLoaded(result);
        }else{
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void loadDataByIds(List<String> ids, LoadCallback<Notification> callback) {
        //
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(final Notification data, final CompletionCallback callback) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
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
    public void saveData(List<Notification> data, CompletionCallback callback) {

    }


    public void addNotificationChange(RealmChangeListener<Notification> changeListener){
        //realm.where(Notification.class).findAll().addChangeListener();
    }


}
