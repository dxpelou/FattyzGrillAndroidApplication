package com.louanimashaun.fattyzgrill;

import android.app.Application;

import com.louanimashaun.fattyzgrill.util.Util;

/**
 * Created by louanimashaun on 25/08/2017.
 */

public class FattyzGrill extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Util.init(this);
    }
}
