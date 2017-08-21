package com.louanimashaun.fattyzgrill;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class OrderFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "OrderMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());    }
}
