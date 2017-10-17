package com.louanimashaun.fattyzgrill.contract;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Notification;

import java.util.List;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public interface NotificationContract {

    interface View extends BaseView{
        void showNotifications(List<Notification> notifications);

        void showNewNotification(Notification notification);

        void showOrderList(List<Meal> meals, List<Integer> quantities);

        void showNoNotifcations();
    }

    interface Presenter extends BasePresenter<View>{

        void loadNotifcations(boolean forceUpdate);

        void loadOrderList(String notificationId);

        void onNotification();

        void acceptOrder(String orderId);

        void openNotification(String notificationID);
    }

}
