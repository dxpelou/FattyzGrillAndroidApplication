package com.louanimashaun.fattyzgrill.contract;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public interface NotificationContract {

    interface View extends BaseView {
        void showNotifications();

        void showNewNotification();

        void showNoNotifcations();
    }

    interface Presenter extends BasePresenter{

        void loadNotifcations();

        void onNotification();
    }
}
