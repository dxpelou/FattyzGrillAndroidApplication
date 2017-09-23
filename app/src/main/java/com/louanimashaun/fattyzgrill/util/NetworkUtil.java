package com.louanimashaun.fattyzgrill.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

/**
 * Created by louanimashaun on 21/09/2017.
 */

public class NetworkUtil {

    private NetworkUtil(){

    }

    public static boolean getConnectionStatus(){

        ConnectivityManager cm = (ConnectivityManager) Util.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

}
