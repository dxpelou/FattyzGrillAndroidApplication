package com.louanimashaun.fattyzgrill.Notifications;

import android.content.Context;
import android.content.SharedPreferences;

import com.louanimashaun.fattyzgrill.util.Util;

/**
 * Created by louanimashaun on 24/08/2017.
 */

public class NotificationSharedPreference  {

    private static NotificationSharedPreference INSTANCE = null;
    private static Context mContext;
    private static final String NOTIFICATION_TAG = "notification_refresh_token";
    private static final String REFRESH_TOKEN_NOT_SET = "null";

     private NotificationSharedPreference(Context context){
         mContext = context;
     }

     public static NotificationSharedPreference getInstance(){

         if(INSTANCE == null ) {
             INSTANCE = new NotificationSharedPreference(Util.getApp());
         }

         return INSTANCE;
     }


     public void saveRefreshToken(String token ){
        SharedPreferences sharedPreferences =  mContext.getSharedPreferences(NOTIFICATION_TAG,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();

         editor.putString(NOTIFICATION_TAG, token);
         editor.commit();
     }

     public static String getRefreshToken(){
         SharedPreferences sharedPreferences =  mContext.getSharedPreferences(NOTIFICATION_TAG,Context.MODE_PRIVATE);
          String t = sharedPreferences.getString(NOTIFICATION_TAG, REFRESH_TOKEN_NOT_SET);
         return t;

     }

}
