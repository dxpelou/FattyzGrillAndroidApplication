package com.louanimashaun.fattyzgrill.model;

import io.realm.RealmObject;

/**
 * Created by louanimashaun on 04/09/2017.
 */

public class RealmString extends RealmObject {
    private String mValue;

    public RealmString(){}

    public RealmString(String value){
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }
}
