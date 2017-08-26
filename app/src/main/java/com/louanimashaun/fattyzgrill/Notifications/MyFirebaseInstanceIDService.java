package com.louanimashaun.fattyzgrill.Notifications;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.louanimashaun.fattyzgrill.util.Util;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG ="MyFirebaseIDSerivce";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        //TODO find way to pass in context into firebase instance id class
        NotificationSharedPreference notificationSharedPreference =
                NotificationSharedPreference.getInstance();

        notificationSharedPreference.saveRefreshToken(refreshedToken);

    }
}
