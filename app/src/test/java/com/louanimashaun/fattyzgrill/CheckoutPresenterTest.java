package com.louanimashaun.fattyzgrill;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.model.RealmString;
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

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by louanimashaun on 30/08/2017.
 */

public class CheckoutPresenterTest {


    //TODO check how to do di in unit tests

    public CheckoutPresenter mCheckoutPresenter;

    @Mock
    private OrderRepository mOrderRepository;

    @Mock
    private MealRepository mMealRepository;

    @Mock
    private CheckoutFragment mCheckoutFragment;

    @Mock
    private DataSource.LoadCallback mLoadCallback;

    @Captor
    private ArgumentCaptor<DataSource.GetCallback> mGetCallbackCaptor;

    @Captor
    private ArgumentCaptor<DataSource.LoadCallback> mLoadCallbackCaptor;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mCheckoutPresenter = new CheckoutPresenter(mOrderRepository, mMealRepository);
        mCheckoutPresenter.takeView(mCheckoutFragment);


    }

    /*
      Test fails due to id's being returned in different order then put in, this could be an
      issue due to the functionality of quantity list which uses order to map quantity with id
     */


    @Test
    public void start_checkIsLoadedIntoView(){
        //set SelectedMeals
        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIdQuantityList());

        //start presenter
        mCheckoutPresenter.start();

        verify(mMealRepository).loadDataByIds(eq(ModelUtil.createStubMealIDList()), mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataLoaded(ModelUtil.createStubMealsList());

        ArgumentCaptor<List<Meal>> mealsCaptor = ArgumentCaptor.forClass(List.class);
        verify(mCheckoutFragment).showCheckout(mealsCaptor.capture(), any(List.class));

        assert ModelUtil.LIST_SIZE == mealsCaptor.getValue().size();
    }

    @Test
    public void start_NotCheckoutLoaded(){
        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIdQuantityList());

        mCheckoutPresenter.start();

       setMealsByIdUnavailable();

        verify(mCheckoutFragment).showNoCheckout();
    }


    @Test
    public void checkoutOrder_repositorySavesOrder(){

        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIdQuantityList());

        mCheckoutPresenter.checkoutOrder();

       setMealsByIdAvailable();

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<DataSource.CompletionCallback> completionCallbackArgumentCaptor = ArgumentCaptor.forClass(DataSource.CompletionCallback.class);

        verify(mOrderRepository).saveData(orderArgumentCaptor.capture(),completionCallbackArgumentCaptor.capture());
        List<RealmString> list = orderArgumentCaptor.getValue().getMealIdsRealm();
        int size = list.size();

        assert size == ModelUtil.LIST_SIZE;

        completionCallbackArgumentCaptor.getValue().onComplete();

        verify(mCheckoutFragment).notifyOrderSent();
    }

    @Test
    public void checkoutOrder_MealsDoNotLoad(){
        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIdQuantityList());

        mCheckoutPresenter.checkoutOrder();

        setMealsByIdAvailable();

        ArgumentCaptor<DataSource.CompletionCallback> completionCallbackCaptor = ArgumentCaptor.forClass(DataSource.CompletionCallback.class);
        verify(mOrderRepository).saveData(any(Order.class), completionCallbackCaptor.capture() );

        completionCallbackCaptor.getValue().onComplete();

        verify(mCheckoutFragment).notifyOrderSent();
    }

    @Test
    public void checkoutOrder_MealDoesNotSave(){
        mCheckoutPresenter.addSelectedMeals(ModelUtil.createStubMealIdQuantityList());

        mCheckoutPresenter.checkoutOrder();

        setMealsByIdAvailable();

        ArgumentCaptor<DataSource.CompletionCallback> completionCallbackCaptor = ArgumentCaptor.forClass(DataSource.CompletionCallback.class);
        verify(mOrderRepository).saveData(any(Order.class), completionCallbackCaptor.capture() );

        completionCallbackCaptor.getValue().onCancel();

        verify(mCheckoutFragment).notifyOrderError();
    }


    public void setMealsByIdAvailable(){
        verify(mMealRepository).loadDataByIds(eq(ModelUtil.createStubMealIDList()), mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataLoaded(ModelUtil.createStubMealsList());
    }

    public void setMealsByIdUnavailable(){
        verify(mMealRepository).loadDataByIds(eq(ModelUtil.createStubMealIDList()), mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataNotAvailable();
    }
}
