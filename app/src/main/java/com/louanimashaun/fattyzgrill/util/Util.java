package com.louanimashaun.fattyzgrill.util;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by louanimashaun on 25/08/2017.
 */

public class Util {

    private static Application sApplication;

    public static void init(@NonNull final Application app){
        Util.sApplication = app;
    }

    public static Application getApp(){
        if(sApplication != null) return sApplication;
        throw new NullPointerException("Utils not initialised");
    }
}
