package com.louanimashaun.fattyzgrill.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class OrderFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "OrderMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationLocalDataSource localDataSource = NotificationLocalDataSource.getInstance();
        Notification notification = new Notification();

        //TODO implement
        notification.setId(UUID.randomUUID().toString());
        notification.setMessage(remoteMessage.getNotification().getTitle());
        notification.setCreatedAt(new Date());
        notification.setType("new_order");
        notification.setTitle(remoteMessage.getNotification().getTitle());
        notification.setExtras(remoteMessage.getNotification().getBody());

        localDataSource.saveData(notification, null);

        OrderNotification.getInstance(Util.getApp()).createNotification();
    }
}
