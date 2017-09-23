package com.louanimashaun.fattyzgrill.contract;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public interface CheckoutContract {

    interface View extends BaseView{
        void showCheckout(List<Meal> meals, List<Integer> quantities);

        void showNoCheckout();

        void notifyOrderSent();

        void notifyOrderError();

        void showQuantityChanged();

    }

    interface Presenter extends BasePresenter{
        void loadCheckout();

        void checkoutOrder();

        void addSelectedMeals(Map<String, Integer> selectedMeals);

        void changeQuantity(String id, boolean isUp);

    }

}
