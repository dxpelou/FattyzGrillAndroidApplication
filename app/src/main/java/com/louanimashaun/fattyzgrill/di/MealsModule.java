package com.louanimashaun.fattyzgrill.di;

import com.louanimashaun.fattyzgrill.contract.MealContract;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by louanimashaun on 25/09/2017.
 */

@Module
public abstract class MealsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MealsFragment mealsFragment();

    @ActivityScoped
    @Binds
    abstract MealContract.Presenter mealsPresenter(MealsPresenter presenter);

}
