package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


       mMealRepository.loadDataByIds(mSelectedMeals, new DataSource.LoadCallback<Meal>() {
           @Override
           public void onDataLoaded(List<Meal> data) {
               mCheckoutFragment.showCheckout(data);
           }

           @Override
           public void onDataNotAvailable() {
                mCheckoutFragment.showNoCheckout();
           }
       });
    }

    @Override
    public void start() {
        loadCheckout();
    }

    @Override
    public void checkoutOrder() {

        final List<Meal> meals = new ArrayList<>();

        mMealRepository.loadDataByIds(mSelectedMeals, new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {
                Order order = createNewOrder(meals, mSelectedMeals);

                mOrderRepository.saveData(order, new DataSource.CompletionCallback() {
                    @Override
                    public void onComplete() {
                        mCheckoutFragment.notifyOrderSent();
                    }

                    @Override
                    public void onCancel() {
                        mCheckoutFragment.notifyOrderError();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                mCheckoutFragment.notifyOrderError();
            }
        });


    }

    @Override
    public void addSelectedMeals( List<String> meals) {
        mSelectedMeals = meals;
    }

    private void getMealsById(List<String> meals, DataSource.GetCallback<Meal> callback){


        //TODO change to loadByIds
        for(String id : meals) {
            mMealRepository.getData(id, callback);
        }
    }

    public Order createNewOrder(List<Meal> meals , List<String> mealIds){
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());

        float total = 0;
        for(Meal meal : meals){
            total += meal.getPrice();
        }

        order.setTotalPrice(total);

        order.setMealIdsRealm(ModelUtil.toRealmStringList(mealIds));
        order.setMealIds(mealIds);

        return order;
    }


}
