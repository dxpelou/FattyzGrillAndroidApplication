package com.louanimashaun.fattyzgrill.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by louanimashaun on 25/09/2017.
 */

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);
}
