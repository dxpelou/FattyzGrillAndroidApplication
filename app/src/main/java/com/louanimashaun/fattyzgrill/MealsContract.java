package com.louanimashaun.fattyzgrill;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * defines how the view and presenter communicates
 */

public interface MealsContract {

    interface View {

        void showMeals(List<Meal> meals);

        void showNoMeals();

        void setPresenter(Presenter presenter);
    }

    interface Presenter{

        void loadMeals(boolean forceUpdate);

        void start();

        void orderCompleted(Order order);
    }
}
