package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.presenter.NotificationPresenter;
import com.louanimashaun.fattyzgrill.view.NotificationFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by louanimashaun on 26/09/2017.
 */

@Module
public abstract class NotficationModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NotificationFragment notificationFragment();

    @ActivityScoped
    @Binds
    abstract NotificationContract.Presenter notificationPresenter(NotificationPresenter presenter);
}
