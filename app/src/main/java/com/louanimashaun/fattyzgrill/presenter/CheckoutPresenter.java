package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.OrderContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public class OrderPresenter implements OrderContract.Presenter {

    private OrderRepository mOrderRepository;

    private MealRepository mMealRepository;
    private CheckoutFragment mCheckoutFragment;
    private List<Meal> selectedMeals;



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
    public void checkoutOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderItems(new RealmList<Meal>((Meal[]) selectedMeals.toArray()));

        float total = 0;
        for(Meal meal : selectedMeals){
            total += meal.getPrice();
        }

        order.setTotalPrice(total);


        mOrderRepository.saveData(order, new DataSource.CompletionCallback() {
            @Override
            public void onComplete() {
                mCheckoutFragment.notifyOrderSent();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void addMeal(Meal meal) {
        if(selectedMeals == null){
            selectedMeals = new ArrayList<>();
        }

        selectedMeals.add(meal);

    }
}
