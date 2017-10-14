package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.notifications.NotificationSharedPreference;
import com.louanimashaun.fattyzgrill.notifications.TokenDataSource;

import dagger.Binds;
import dagger.Module;

/**
 * Created by louanimashaun on 26/09/2017.
 */

@Module
public abstract class TokenModule {
    @Binds
    abstract TokenDataSource provideRefreshTokenDataSource (NotificationSharedPreference notificationSharedPreference);
}

