package com.louanimashaun.fattyzgrill.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by louanimashaun on 25/06/2017.
 */

public class UserConfig {
    private static Map<String, WeakReference<Boolean>> configMap = null;
    public static final String IS_ADMIN_KEY = "is_admin";

    private static Map<String, WeakReference<Boolean>> getConfigMapInstance(){
        if(configMap == null){
            configMap = new HashMap<>();
        }
        return configMap;
    }

    public void setUserAdmin(boolean isAdmin){
        WeakReference<Boolean> isUserAdmin = new WeakReference<Boolean>(isAdmin);

        configMap.put(IS_ADMIN_KEY, isUserAdmin);
    }
    public boolean isAdmin(){
        return configMap.get(IS_ADMIN_KEY).get();
    }

}
