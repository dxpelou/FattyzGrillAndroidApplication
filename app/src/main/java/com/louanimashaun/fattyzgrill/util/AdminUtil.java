package com.louanimashaun.fattyzgrill.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by louanimashaun on 26/08/2017.
 */

public class AdminUtil {

    private static Context mContext;
    private static String Admin_TAG = "admin";


    private AdminUtil (Context context){
        mContext = context;
    }

    public static void setAdminStatus(boolean isAdmin){
        if(mContext == null){
            mContext = Util.getApp();
        }

        SharedPreferences sharedPreferences =  mContext.getSharedPreferences(Admin_TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Admin_TAG, isAdmin);
        editor.commit();
    }


    public static boolean isAdmin(){
        if(mContext == null){
            mContext = Util.getApp();
        }

        SharedPreferences sharedPreferences =  mContext.getSharedPreferences(Admin_TAG,Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean(Admin_TAG, false);
        return isAdmin;
    }
}
