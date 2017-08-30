package com.louanimashaun.fattyzgrill;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.realm.RealmList;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by louanimashaun on 30/08/2017.
 */

public class CheckoutPresenterTest {

    private CheckoutPresenter mCheckoutPresenter;

    @Mock
    private OrderRepository mOrderRepository;

    @Mock
    private MealRepository mMealRepository;

    @Mock
    private CheckoutFragment mCheckoutFragment;

    @Captor
    private ArgumentCaptor<DataSource.GetCallback> mGetCallbackCaptor;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mCheckoutPresenter = new CheckoutPresenter(
                mOrderRepository, mMealRepository, mCheckoutFragment);
    }


    @Test
    public void start_checkIsLoadedIntoView(){

    }

    @Test
    public void start_NotCheckoutLoaded(){

    }

    @Test
    public void addSelectedMeals_MealsRepositoryGetsData(){

    }

    @Test
    public void checkoutOrder_repositorySavesOrder(){
        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIDList());

        mCheckoutPresenter.checkoutOrder();

        verify(mMealRepository, times(ModelUtil.LIST_SIZE)).getData(eq("0000"), mGetCallbackCaptor.capture());

        mGetCallbackCaptor.getValue().onDataLoaded(ModelUtil.createStubMeal());

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<DataSource.CompletionCallback> completionCallbackArgumentCaptor = ArgumentCaptor.forClass(DataSource.CompletionCallback.class);

        verify(mOrderRepository).saveData(orderArgumentCaptor.capture(),completionCallbackArgumentCaptor.capture());
        RealmList<Meal> list = orderArgumentCaptor.getValue().getOrderItems();
        int size = list.size();

        assert size == ModelUtil.LIST_SIZE;

        completionCallbackArgumentCaptor.getValue().onComplete();

        verify(mCheckoutFragment).notifyOrderSent();
    }
}
