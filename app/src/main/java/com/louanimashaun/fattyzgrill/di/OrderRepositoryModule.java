package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.OrdersRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by louanimashaun on 26/09/2017.
 */

@Module
public abstract class OrderRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract DataSource provideTasksLocalDataSource(OrdersLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract DataSource provideTasksRemoteDataSource(OrdersRemoteDataSource dataSource);
}
