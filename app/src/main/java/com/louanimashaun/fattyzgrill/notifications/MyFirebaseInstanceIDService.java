package com.louanimashaun.fattyzgrill.notifications;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by louanimashaun on 21/08/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG ="MyFirebaseIDSerivce";

    @Inject
    Lazy<NotificationSharedPreference> notificationSharedPreference;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        //TODO find way to pass in context into firebase instance id clas

        notificationSharedPreference.get().saveRefreshToken(refreshedToken);

    }
}
