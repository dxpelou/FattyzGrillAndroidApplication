package com.louanimashaun.fattyzgrill.presenter;

import android.widget.Toast;

import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.notifications.TokenDataSource;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.util.Util;
import com.louanimashaun.fattyzgrill.view.Listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 27/08/2017.
 */
@ActivityScoped
public class CheckoutPresenter implements CheckoutContract.Presenter {

    private OrderRepository mOrderRepository;
    private TokenDataSource mNotificationSharedPreference;

    private MealRepository mMealRepository;
    private CheckoutContract.View mCheckoutFragment;
    private Map<String,Integer> mSelectedMeals;
    private List<String> mMealIds;
    private Listeners.CheckoutChangedListener mCheckoutChangedListener;


    @Inject
    public CheckoutPresenter(OrderRepository orderRepository, MealRepository mealRepository, TokenDataSource notificationSharedPreference){

        mOrderRepository = checkNotNull(orderRepository);
        mMealRepository = checkNotNull(mealRepository);
        mNotificationSharedPreference = checkNotNull(notificationSharedPreference);

    }

    @Override
    public void loadCheckout() {

        if(mSelectedMeals == null){
//            mCheckoutFragment.showNoCheckout();
            return;
        }

        List<String> mealIds = new ArrayList(mSelectedMeals.keySet());

       mMealRepository.loadDataByIds(mealIds, new DataSource.LoadCallback<Meal>() {
           @Override
           public void onDataLoaded(List<Meal> data) {
               List<Integer> quanitiesList = new ArrayList<Integer>(mSelectedMeals.values());
               mCheckoutFragment.showCheckout(data, quanitiesList );
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
    public void takeView(CheckoutContract.View view) {
        mCheckoutFragment = checkNotNull(view);
    }


    @Override
    public void checkoutOrder() {

        final List<Meal> meals = new ArrayList<>();

        if (mSelectedMeals ==  null){
            return;
        }

        mMealRepository.loadDataByIds(new ArrayList(mSelectedMeals.keySet()), new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {
                Order order = createNewOrder(data,mSelectedMeals);

                if(order.getSenderNotificationToken() == null){
                    Toast.makeText(Util.getApp(), "Unable to make order, please try again later", Toast.LENGTH_SHORT).show();
                    return;
                }

                mOrderRepository.saveData(order, new DataSource.CompletionCallback() {
                    @Override
                    public void onComplete() {
                        mCheckoutFragment.notifyOrderSent();
                        mSelectedMeals = new HashMap<String, Integer>();
                        mCheckoutChangedListener.onCheckoutChanged(mSelectedMeals);
                        loadCheckout();
                    }

                    @Override
                    public void onCancel() {
                        mCheckoutFragment.notifyOrderError();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });



    }

    @Override
    public void addSelectedMeals( Map<String,Integer> meals) {
        mSelectedMeals = meals;
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

        mCheckoutChangedListener.onCheckoutChanged(mSelectedMeals);

        loadCheckout();
    }

    @Override
    public void addCheckoutChangeListener(Listeners.CheckoutChangedListener listener) {
        mCheckoutChangedListener = checkNotNull(listener);
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

        String token = mNotificationSharedPreference.getRefreshToken();

        order.setSenderNotificationToken(token);
        order.setCreatedAt(Calendar.getInstance().getTime());


        return order;
    }

}
