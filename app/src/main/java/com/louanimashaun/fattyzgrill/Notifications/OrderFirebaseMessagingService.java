package com.louanimashaun.fattyzgrill.Notifications;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.Util;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class OrderFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "OrderMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            /* Check if data bneeds to be processed by long running job, if true,
              for long running task (10 seconds or more) use Firebase Job dispatcher)
              else handle messasge within 10 seconds
            */

            OrderNotification.getInstance(Util.getApp()).createNotification();
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
