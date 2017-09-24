package com.louanimashaun.fattyzgrill.model;

import io.realm.RealmObject;

/**
 * Created by louanimashaun on 23/09/2017.
 */

public class RealmInteger extends RealmObject{
    private int mValue;

    public RealmInteger(){}

    public RealmInteger(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }
}
