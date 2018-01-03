package com.louanimashaun.fattyzgrill.contract;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.Calendar;
import java.util.List;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public interface NotificationContract {

    interface View extends BaseView{
        void showNotifications(List<Notification> notifications, List<Order> orders);

        void showNewNotification(Notification notification);

        void showOrderList(List<Meal> meals, Order order, String notificationType);

        void showNoNotifcations();
    }

    interface Presenter extends BasePresenter<View>{

        void loadNotifcations(boolean forceUpdate);

        void loadOrderList(String notificationId);

        void onNotification();

        void acceptOrder(String orderId, Calendar collectionTime);

        void openNotification(String notificationID);
    }

}
