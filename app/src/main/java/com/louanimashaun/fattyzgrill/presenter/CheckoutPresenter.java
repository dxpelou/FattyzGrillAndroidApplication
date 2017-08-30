package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
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

public class CheckoutPresenter implements CheckoutContract.Presenter {

    private OrderRepository mOrderRepository;

    private MealRepository mMealRepository;
    private CheckoutFragment mCheckoutFragment;
    private List<String> mSelectedMeals;



    public CheckoutPresenter(OrderRepository orderRepository, MealRepository mealRepository,
                             CheckoutFragment checkoutFragment){

        mOrderRepository = checkNotNull(orderRepository);
        mCheckoutFragment = checkNotNull(checkoutFragment);
        mMealRepository = checkNotNull(mealRepository);
    }

    @Override
    public void loadCheckout() {

        if(mSelectedMeals == null){
            mCheckoutFragment.showNoCheckout();
            return;
        }

        final List<Meal> checkoutList = new ArrayList<>();

        for(String id : mSelectedMeals) {
            mMealRepository.getData(id, new DataSource.GetCallback<Meal>() {
                @Override
                public void onDataLoaded(Meal data) {
                    checkoutList.add(data);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }

        if(checkoutList.size() == 0){
            mCheckoutFragment.showNoCheckout();
            return;
        }

        mCheckoutFragment.showCheckout(checkoutList);
    }

    @Override
    public void start() {
        loadCheckout();
    }

    @Override
    public void checkoutOrder() {

        final List<Meal> meals = new ArrayList<>();

        getMealsById(mSelectedMeals, new DataSource.GetCallback<Meal>() {
            @Override
            public void onDataLoaded(Meal data) {
                meals.add(data);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());

        RealmList<Meal> realmList = new RealmList<Meal>();
        realmList.addAll(meals);
        order.setOrderItems(realmList);
       // order.setOrderItems(new RealmList<Meal>((Meal[]) meals.toArray()));

        float total = 0;
        for(Meal meal : meals){
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
    public void addSelectedMeals( List<String> meals) {
        mSelectedMeals = meals;
    }

    private void getMealsById(List<String> meals, DataSource.GetCallback<Meal> callback){

        for(String id : meals) {
            mMealRepository.getData(id, callback);
        }
    }


}
