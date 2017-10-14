package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.PreconditonUtil;
import com.louanimashaun.fattyzgrill.view.NotificationFragment;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 10/09/2017.
 */

@ActivityScoped
public class NotificationPresenter implements NotificationContract.Presenter {

    private NotificationContract.View mNotificationView;

    private NotificationLocalDataSource mLocalDataSource;

    @Inject
    public NotificationPresenter( NotificationLocalDataSource localDataSource){
       mLocalDataSource = checkNotNull(localDataSource);
    }

    @Override
    public void start() {
        loadNotifcations(true);

    }

    @Override
    public void takeView(NotificationContract.View view) {
        mNotificationView = checkNotNull(view);
    }

    @Override
    public void loadNotifcations(boolean forceUpdate) {

        mLocalDataSource.loadData(new DataSource.LoadCallback<Notification>() {
            @Override
            public void onDataLoaded(List<Notification> data) {
                mNotificationView.showNotifications(data);
            }

            @Override
            public void onDataNotAvailable() {
                mNotificationView.showNoNotifcations();
            }
        });
    }

    @Override
    public void loadOrderList() {

    }

    @Override
    public void onNotification() {
        //mLocalDataSource.addNotificationChange();
    }
}
