package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class NotificationPresenter implements NotificationContract.Presenter {
    @Override
    public void start() {
        loadNotifcations();
    }

    @Override
    public void loadNotifcations() {

    }

    @Override
    public void onNotification() {

    }
}
