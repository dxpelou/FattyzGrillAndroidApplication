package com.louanimashaun.fattyzgrill.notifications;

import android.content.Context;
import android.content.SharedPreferences;

import com.louanimashaun.fattyzgrill.util.Util;

import javax.inject.Inject;

/**
 * Created by louanimashaun on 24/08/2017.
 */

public class NotificationSharedPreference implements TokenDataSource {

    private static NotificationSharedPreference INSTANCE = null;
    private static Context mContext;
    private static final String NOTIFICATION_TAG = "com.louanimashaun.fattyzgrill.notification_refresh_token";
    private static final String REFRESH_TOKEN_NOT_SET = "null";


    public static NotificationSharedPreference getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NotificationSharedPreference();
        }

        return INSTANCE;
    }

    @Inject
     public NotificationSharedPreference(){
         mContext = Util.getApp();
     }


     @Override
     public void saveRefreshToken(String token){
        SharedPreferences sharedPreferences =  mContext.getSharedPreferences(NOTIFICATION_TAG,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();

         editor.putString(NOTIFICATION_TAG, token);
         editor.apply();
     }

     @Override
     public  String getRefreshToken(){
         SharedPreferences sharedPreferences =  mContext.getSharedPreferences(NOTIFICATION_TAG,Context.MODE_PRIVATE);
          String t = sharedPreferences.getString(NOTIFICATION_TAG, null);
         return t;

     }

}
