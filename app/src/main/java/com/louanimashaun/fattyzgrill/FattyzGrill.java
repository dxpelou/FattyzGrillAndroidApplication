package com.louanimashaun.fattyzgrill;

import android.app.Activity;
import android.app.Application;

import com.louanimashaun.fattyzgrill.di.AppComponent;
import com.louanimashaun.fattyzgrill.di.DaggerAppComponent;
import com.louanimashaun.fattyzgrill.util.Util;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by louanimashaun on 25/08/2017.
 */

public class FattyzGrill extends DaggerApplication{


    @Override
    public void onCreate() {
        super.onCreate();
        Util.init(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
