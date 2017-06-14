package com.louanimashaun.fattyzgrill.util;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * class is used to throw an exception early rather than later
 *
 */

public class PreconditonUtil {

    public static <T> T checkNotNull(T reference){
        if(reference == null){
            throw new NullPointerException();
        }else{
            return reference;
        }
    }
}
