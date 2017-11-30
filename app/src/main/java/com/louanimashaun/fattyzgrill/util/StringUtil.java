package com.louanimashaun.fattyzgrill.util;


import java.text.NumberFormat;
import java.util.Locale;

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

    public static String formatPrice(float price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
        String moneyString = formatter.format(price);

        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1) {
                moneyString = moneyString.substring(0, centsIndex);
            }
        }

        return moneyString;
    }
 }
