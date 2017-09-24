package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.PreconditonUtil;
import com.louanimashaun.fattyzgrill.view.NotificationFragment;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class NotificationPresenter implements NotificationContract.Presenter {

    private NotificationFragment mNotificationView;

    private NotificationLocalDataSource mLocalDataSource;

    public NotificationPresenter(NotificationFragment fragment, NotificationLocalDataSource localDataSource){
       mNotificationView =  PreconditonUtil.checkNotNull(fragment);
       mLocalDataSource = PreconditonUtil.checkNotNull(localDataSource);
    }

    @Override
    public void start() {
        loadNotifcations(true);

    }

    @Override
    public void loadNotifcations(boolean forceUpdate) {

        mLocalDataSource.loadData(new DataSource.LoadCallback<Notification>() {
            @Override
            public void onDataLoaded(List<Notification> data) {



               /* data.sort(new Comparator<Notification>() {
                    @Override
                    public int compare(Notification t1, Notification t2) {
                        Date date1  = t1.getCreatedAt();
                        Date date2 = t2.getCreatedAt();

                        if(date1.before(date2)) return 1;

                        if(date1.equals(date2)) return 0;

                        return -1;
                    }
                });*/

                mNotificationView.showNotifications(data);
            }

            @Override
            public void onDataNotAvailable() {
                mNotificationView.showNoNotifcations();
            }
        });
    }

    @Override
    public void onNotification() {
        //mLocalDataSource.addNotificationChange();
    }
}
