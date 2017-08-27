package com.louanimashaun.fattyzgrill.contract;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * defines how the view and presenter communicates
 */

public interface MealContract {

    interface View extends BaseView {

        void showMeals(List<Meal> meals);

        void showNoMeals();

    }

    interface Presenter extends BasePresenter{

        void loadMeals(boolean forceUpdate);

        void AddMealToCheckout(Meal meal);
    }
}
