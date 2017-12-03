package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.AddressActivity;
import com.louanimashaun.fattyzgrill.MealActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by louanimashaun on 25/09/2017.
 */
@Module
public abstract class ActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {MealsModule.class,
            CheckoutModule.class,
            NotficationModule.class,
            TokenModule.class})
    abstract MealActivity bindMealActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddressActivity bindAdressActivity();

}
