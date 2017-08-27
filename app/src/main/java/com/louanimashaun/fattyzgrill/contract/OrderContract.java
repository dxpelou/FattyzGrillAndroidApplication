package com.louanimashaun.fattyzgrill.contract;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public interface OrderContract  {

    interface View extends BaseView{
        void showCheckout(List<Meal> meals);

        void showNoCheckout();

    }

    interface Presenter extends BasePresenter{
        void loadCheckout();

        void checkoutOrder(Order order);
    }
}
