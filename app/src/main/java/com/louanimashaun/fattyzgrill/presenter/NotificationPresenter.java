package com.louanimashaun.fattyzgrill.presenter;

import android.widget.Toast;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.notifications.OrderNotification;
import com.louanimashaun.fattyzgrill.util.AdminUtil;
import com.louanimashaun.fattyzgrill.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
            public void onDataLoaded(final List<Notification> notifications) {
                final List<String> orderIds = new ArrayList<>();

                for(Notification notification : notifications){
                    //TODO change Extras member variable to orderId
                    orderIds.add(notification.getExtras());
                }

                mOrderRepository.refreshData();

                mOrderRepository.loadDataByIds(orderIds, new DataSource.LoadCallback<Order>() {
                    @Override
                    public void onDataLoaded(List<Order> orders) {
                        List<OrderNotificationPair> pairs = createNotificationOrderPair(orders, notifications);
                        pairs = sortNotifications(pairs);

                        List<Notification> notificationsList = new ArrayList<>();
                        List<Order> ordersList = new ArrayList<>();

                        for (OrderNotificationPair pair : pairs){
                            notificationsList.add(pair.notification);
                            ordersList.add(pair.order);
                        }

                        mNotificationView.showNotifications(notificationsList, ordersList);
                    }

                    @Override
                    public void onDataNotAvailable() {

                        mNotificationView.showNoNotifcations();
                    }
                });


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
            public void onDataLoaded(final Notification notificationData) {
                String orderId = notificationData.getExtras();
                final String notificationType = notificationData.getType();

                mOrderRepository.getData(orderId, new DataSource.GetCallback<Order>() {
                    @Override
                    public void onDataLoaded(final Order orderData) {
                        mMealRepository.loadDataByIds(orderData.getMealIds(), new DataSource.LoadCallback<Meal>() {
                            @Override
                            public void onDataLoaded(List<Meal> data) {
                                mNotificationView.showOrderList(data, orderData, notificationType);
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
            public void onDataNotAvailable() {
                return;
            }
        });

        openNotification(notificationId);

    }

    @Override
    public void acceptOrder(String id, final Calendar collectionTime){

        if(!AdminUtil.isAdmin()) {
            return;
        }
        mOrderRepository.getData(id, new DataSource.GetCallback<Order>() {
            @Override
            public void onDataLoaded(final Order orderData) {

                if(orderData.isOrderAccepted()){
                    Toast.makeText(Util.getApp(), "Order has already been accepted", Toast.LENGTH_SHORT);
                }
                // you have to create a new object, unless you have to work with a realm managed object
                Order acceptedOrder = new Order();

                acceptedOrder.setId(orderData.getId());
                acceptedOrder.setMealIds(orderData.getMealIds());
                acceptedOrder.setMealIdsRealm(orderData.getMealIdsRealm());
                acceptedOrder.setQuantities(orderData.getQuantities());
                acceptedOrder.setQuantitiesRealm(orderData.getQuantitiesRealm());
                acceptedOrder.setTotalPrice(orderData.getTotalPrice());
                acceptedOrder.setSenderNotificationToken(orderData.getSenderNotificationToken());
                acceptedOrder.setUserId(orderData.getUserId());
                acceptedOrder.setOrderAccepted(true);
                acceptedOrder.setAcceptedAt(Calendar.getInstance().getTime());
                acceptedOrder.setCollectionAt(collectionTime.getTime());
                acceptedOrder.setCreatedAt(orderData.getCreatedAt());




                mOrderRepository.saveData(acceptedOrder, null);

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


    public class OrderNotificationPair{

        Notification notification;
        Order order;
    }

    public List<OrderNotificationPair> sortNotifications(List<OrderNotificationPair> notifications){
        Collections.sort(notifications, new Comparator<OrderNotificationPair>() {
            @Override
            public int compare(OrderNotificationPair o1, OrderNotificationPair o2) {
                Date date1 = o1.order.getCreatedAt();
                Date date2 = o2.order.getCreatedAt();

                if(o1.notification.getType() == "order_accepted"){
                    date1 = o1.order.getAcceptedAt();
                }

                if(o2.notification.getType() == "order_accepted"){
                    date2 = o2.order.getAcceptedAt();
                }

                if(date1.before(date2)){
                    return 1;
                }else if (date2.before(date1)){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return notifications;
    }


    public List<OrderNotificationPair> createNotificationOrderPair(List<Order> orders, List<Notification> notifications){
        List<OrderNotificationPair> pairs = new ArrayList<>();

        for (Order order : orders){
            OrderNotificationPair pair = new OrderNotificationPair();
            pair.order = order;
            pairs.add(pair);
        }

        for(int i = 0 ; i < notifications.size(); i++){
            Notification notification = notifications.get(i);
            OrderNotificationPair onp = pairs.get(i);

            onp.notification = notification;
            pairs.set(i, onp);
        }

        return pairs;
    }
}
