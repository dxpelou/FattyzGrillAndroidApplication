package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by louanimashaun on 25/09/2017.
 */

@Module
public abstract class MealsRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract DataSource provideMealsLocalDataSource(MealsLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract DataSource provideMealsRemoteDataSource(MealsRemoteDataSource dataSource);
}
