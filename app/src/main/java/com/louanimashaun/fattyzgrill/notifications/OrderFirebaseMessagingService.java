package com.louanimashaun.fattyzgrill.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class OrderFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TYPE_KEY= "type";
    private static final String ORDER_ID="orderID";
    private static final String CREATED_AT="createdAt";
    private static final String TITLE = "Fattyz Grill";
    private static final String NEW_ORDER_MESSAGE = "New order recieved";
    private static final String ORDER_ACCEPTED_MESSAGE = "Your order has been accepted";

    private static final String TAG = "OrderMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationLocalDataSource localDataSource = NotificationLocalDataSource.getInstance();
        Notification notification = parseRemoteMessage(remoteMessage);

        if(notification == null) return;



        //TODO implement
        /*notification.setId(UUID.randomUUID().toString());
        notification.setMessage(remoteMessage.getNotification().getTitle());
        notification.setCreatedAt(new Date());
        notification.setType("new_order");
        notification.setTitle(remoteMessage.getNotification().getTitle());
        notification.setExtras(remoteMessage.getNotification().getBody());
        notification.setHasBeenOpened(false);*/

        localDataSource.saveData(notification, null);

        OrderNotification.getInstance(Util.getApp()).createNotification(notification);
    }


    private Notification parseRemoteMessage(RemoteMessage remoteMessage){
        if(remoteMessage == null) return null;
        Map<String, String> data = remoteMessage.getData();
        Notification notification = new Notification();

        notification.setId(UUID.randomUUID().toString());
        notification.setType(data.get(TYPE_KEY));
        notification.setTitle(TITLE);
        notification.setExtras(data.get(ORDER_ID));
        notification.setHasBeenOpened(false);

        String type = data.get(TYPE_KEY);

        if(type.equals("new_order")){
            notification.setMessage(NEW_ORDER_MESSAGE);
        }

        if(type.equals("order_accepted")){
            notification.setMessage(ORDER_ACCEPTED_MESSAGE);
        }

        return notification;

    }
}
