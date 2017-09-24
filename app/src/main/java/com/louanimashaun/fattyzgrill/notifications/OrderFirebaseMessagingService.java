package com.louanimashaun.fattyzgrill.notifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.Date;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class OrderFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "OrderMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationLocalDataSource localDataSource = NotificationLocalDataSource.getInstance();
        Notification notification = new Notification();



        if (remoteMessage.getData().size()  < 0 || remoteMessage.getNotification() == null) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            return ;

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        //TODO implement
        notification.setId(null);
        notification.setMessage(null);
        notification.setCategory(null);
        notification.setCreatedAt(new Date(remoteMessage.getSentTime()));
        notification.setTopic(null);

        //localDataSource.saveData(notification, null);

        OrderNotification.getInstance(Util.getApp()).createNotification();

         /* Check if data needs to be processed by long running job, if true,
              for long running task (10 seconds or more) use Firebase Job dispatcher)
              else handle messasge within 10 seconds
            */

    }
}
