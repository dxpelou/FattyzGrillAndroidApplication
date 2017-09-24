package com.louanimashaun.fattyzgrill.util;

import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;

/**
 * Created by louanimashaun on 11/09/2017.
 */

public class StringUtil {

    public static String convertToCamelCase(String str){
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
            for(String s :strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap + " ");
            }

        return builder.toString();
    }
 }
