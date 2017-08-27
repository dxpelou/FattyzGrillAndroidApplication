package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.OrderContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public class OrderPresenter implements OrderContract.Presenter {

    private OrderRepository mOrderRepository;

    private MealRepository mMealRepository;
    private CheckoutFragment mCheckoutFragment;


    public OrderPresenter(OrderRepository orderRepository,
                          CheckoutFragment checkoutFragment){

        mOrderRepository = checkNotNull(orderRepository);
        mCheckoutFragment = checkNotNull(checkoutFragment);
    }

    @Override
    public void loadCheckout() {

        //if data is loaded from remote checkouts will be lost
        mMealRepository.loadData(new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {
                for(int i = 0; i < data.size(); i++){
                    Meal meal = data.get(i);
                    if(!meal.isCheckedOut()){

                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void start() {
        loadCheckout();
    }

    @Override
    public void checkoutOrder(Order order) {
        mOrderRepository.saveData(order, new DataSource.CompletionCallback() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
