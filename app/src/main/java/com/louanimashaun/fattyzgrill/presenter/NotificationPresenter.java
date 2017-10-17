package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.PreconditonUtil;
import com.louanimashaun.fattyzgrill.util.Util;
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

    private NotificationLocalDataSource mNotificationLocalDataSource;

    private OrderRepository mOrderRepository;

    private MealRepository mMealRepository;

    @Inject
    public NotificationPresenter( NotificationLocalDataSource localDataSource, OrderRepository orderRepository, MealRepository mealRepository){
       mNotificationLocalDataSource = checkNotNull(localDataSource);
        mOrderRepository = checkNotNull(orderRepository);
        mMealRepository = checkNotNull(mealRepository);
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

        mNotificationLocalDataSource.loadData(new DataSource.LoadCallback<Notification>() {
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
    public void loadOrderList(String notificationId) {



        mNotificationLocalDataSource.getData(notificationId, new DataSource.GetCallback<Notification>() {
            @Override
            public void onDataLoaded(Notification notificationData) {
                String orderId = notificationData.getExtras();

                mOrderRepository.getData(orderId, new DataSource.GetCallback<Order>() {
                    @Override
                    public void onDataLoaded(final Order orderData) {
                        mMealRepository.loadDataByIds(orderData.getMealIds(), new DataSource.LoadCallback<Meal>() {
                            @Override
                            public void onDataLoaded(List<Meal> data) {
                                mNotificationView.showOrderList(data, orderData.getQuantities());
                            }

                            @Override
                            public void onDataNotAvailable() {

                            }
                        });
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        openNotification(notificationId);

    }

    @Override
    public void acceptOrder(String id){

        mOrderRepository.getData(id, new DataSource.GetCallback<Order>() {
            @Override
            public void onDataLoaded(final Order orderData) {

                mMealRepository.loadDataByIds(orderData.getMealIds(), new DataSource.LoadCallback<Meal>() {
                    @Override
                    public void onDataLoaded(List<Meal> mealData) {
                        mNotificationView.showOrderList(mealData, orderData.getQuantities());
                    }

                    @Override
                    public void onDataNotAvailable() {
                        return;
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                return;
            }
        });

    }

    @Override
    public void openNotification(String notificationID) {
        mNotificationLocalDataSource.getData(notificationID, new DataSource.GetCallback<Notification>() {
            @Override
            public void onDataLoaded(Notification data) {
                if(data.isHasBeenOpened()) return;

                Notification newNotification = new Notification();

                newNotification.setId(data.getId());
                newNotification.setTitle(data.getTitle());
                newNotification.setMessage(data.getMessage());
                newNotification.setType(data.getType());
                newNotification.setExtras(data.getExtras());
                newNotification.setCreatedAt(data.getCreatedAt());
                newNotification.setHasBeenOpened(true);
                mNotificationLocalDataSource.saveData(newNotification, null);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void onNotification() {
        //mLocalDataSource.addNotificationChange();
    }
}
