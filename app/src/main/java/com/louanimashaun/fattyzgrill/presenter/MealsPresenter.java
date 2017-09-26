package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.contract.MealContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import java.util.List;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *Presenter layer of application
 */

@ActivityScoped
public class MealsPresenter implements MealContract.Presenter{

    private final MealRepository mMealRepository;
    private MealContract.View mMealsView;

    @Inject
    public MealsPresenter(@NonNull MealRepository mealRepository){
        mMealRepository = checkNotNull(mealRepository);
    }

    @Override
    public void start() {
        loadMeals(true);
    }

    @Override
    public void takeView(MealContract.View view) {
        mMealsView = checkNotNull(view);
    }

    @Override
    public void loadMeals(boolean forceUpdate) {
        if(forceUpdate){
            mMealRepository.refreshData();
        }

//        /*mMealRepository.load(new DataSource.LoadCallback() {
//            @Override
//            public void onDataLoaded(List data) {
////                List<Meal> sortedMeal = ModelUtil.sortMealsByCategory(data);
//                mMealsView.showMeals(data);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                mMealsView.showNoMeals();
//            }
//        });*/

        mMealRepository.loadData(new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {
                mMealsView.showMeals(data);
            }

            @Override
            public void onDataNotAvailable() {
                mMealsView.showNoMeals();
            }
        });
    }

    @Override
    public void findMeal(String ids) {

    }
}
