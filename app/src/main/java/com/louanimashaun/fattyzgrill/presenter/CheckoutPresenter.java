package com.louanimashaun.fattyzgrill.presenter;

import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.notifications.NotificationSharedPreference;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.util.PreconditonUtil;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public class CheckoutPresenter implements CheckoutContract.Presenter {

    private OrderRepository mOrderRepository;

    private MealRepository mMealRepository;
    private CheckoutFragment mCheckoutFragment;
    private Map<String,Integer> mSelectedMeals;
    private List<String> mMealIds;


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

       mMealRepository.loadDataByIds(mMealIds, new DataSource.LoadCallback<Meal>() {
           @Override
           public void onDataLoaded(List<Meal> data) {
               List<Integer> quanitiesList = new ArrayList<Integer>(mSelectedMeals.values());
               mCheckoutFragment.showCheckout(data,quanitiesList );
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
        Order order = createNewOrder(meals,mSelectedMeals);

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
    public void addSelectedMeals( Map<String,Integer> meals) {
        mSelectedMeals = meals;
        if(mSelectedMeals != null)
        mMealIds = new ArrayList<String>(mSelectedMeals.keySet());
    }

    @Override
    public void changeQuantity(String id, boolean isUp) {
        if(!mSelectedMeals.containsKey(id)) mSelectedMeals.put(id, 1);

        int quantity = mSelectedMeals.get(id);
        if(isUp){
            quantity++;
        }else{
            quantity--;
        }

        if(quantity == 0){
            mSelectedMeals.remove(id);
        }else{
            mSelectedMeals.put(id, quantity);
        }

        loadCheckout();
    }

    private void getMealsById(List<String> meals, DataSource.GetCallback<Meal> callback){


        //TODO change to loadByIds
        for(String id : meals) {
            mMealRepository.getData(id, callback);
        }
    }

    public Order createNewOrder(List<Meal> meals , Map<String,Integer> mealIds){
        List<String> keys = new ArrayList<String>(mealIds.keySet());
        List<Integer> values = new ArrayList<Integer>(mealIds.values());

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());

        float total = 0;
        for(Meal meal : meals){
            total += meal.getPrice();
        }

        order.setTotalPrice(total);

        order.setMealIdsRealm(ModelUtil.toRealmStringList(keys));
        order.setMealIds(keys);

        order.setQuantitiesRealm(ModelUtil.toRealmIntegerList(values));
        order.setQuantities(values);

        order.setSenderNotifcationToken(NotificationSharedPreference.getRefreshToken());

        return order;
    }

}
