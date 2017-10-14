package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
import com.louanimashaun.fattyzgrill.contract.MealContract;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by louanimashaun on 26/09/2017.
 */

@Module
public abstract class CheckoutModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CheckoutFragment checkoutFragment();

    @ActivityScoped
    @Binds
    abstract CheckoutContract.Presenter checkoutPresenter(CheckoutPresenter presenter);
}
