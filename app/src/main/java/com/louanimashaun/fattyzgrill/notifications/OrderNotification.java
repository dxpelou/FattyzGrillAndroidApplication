package com.louanimashaun.fattyzgrill.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.util.Util;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by louanimashaun on 25/08/2017.
 */

public class OrderNotification {

    private static Context mConext;
    private static OrderNotification INSTANCE;
    private static final int PRIORITY_MAX = 2;


    public static OrderNotification getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new OrderNotification(context);
        }
        return  INSTANCE;
    }

    private OrderNotification(Context context){
        mConext = context;
    }



    public void createNotification(Notification notification){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Util.getApp())
                .setSmallIcon(R.drawable.logof)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getMessage())
                .setPriority(PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //TODO find the difference betwteen notificationManager and notificationManagerCompat
        NotificationManager notificationManager = (NotificationManager) mConext
                .getSystemService(NOTIFICATION_SERVICE);

        int notificationID = 0;
        notificationManager.notify(notificationID, mBuilder.build());
    }
}
