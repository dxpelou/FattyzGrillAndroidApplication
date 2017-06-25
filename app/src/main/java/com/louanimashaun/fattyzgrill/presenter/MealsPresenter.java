package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.MealsContract;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealsRepository;
import com.louanimashaun.fattyzgrill.data.OrdersRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *Presenter layer of application
 */

public class MealsPresenter implements MealsContract.Presenter{

    private final MealsRepository mMealsRepository;
    private final OrdersRepository mOrdersRepository;
    private final MealsFragment mMealsView;

    public MealsPresenter(@NonNull MealsRepository mealsRepository, @NonNull OrdersRepository ordersRepository, MealsFragment mealsView){
        mMealsRepository = checkNotNull(mealsRepository);
        mOrdersRepository = checkNotNull(ordersRepository);
        mMealsView = checkNotNull(mealsView);
        mMealsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadMeals(true);
    }

    @Override
    public void loadMeals(boolean forceUpdate) {
        if(forceUpdate){
            mMealsRepository.refreshData();
        }

        mMealsRepository.loadData(new DataSource.LoadCallBack<Meal>() {
            @Override
            public void LoadData(List<Meal> data) {
                mMealsView.showMeals(data);
            }

            @Override
            public void onDataNotAvailable() {
                mMealsView.showNoMeals();
            }
        });
    }

    @Override
    public void orderCompleted(Order order) {
        mOrdersRepository.saveData(order, new DataSource.CompletionCallback() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
