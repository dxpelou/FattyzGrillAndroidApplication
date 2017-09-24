package com.louanimashaun.fattyzgrill;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public class MealsPresenterTest {

    private MealsPresenter mPresenter;

    @Mock
    private MealRepository mMealRepository;

    @Mock
    private MealsFragment mMealsFragment;

    @Captor
    private ArgumentCaptor<DataSource.LoadCallback> mLoadCallbackCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mPresenter = new MealsPresenter(mMealRepository, mMealsFragment);
    }

    @Test
    public void loadsMeals_loadsIntoView(){
        mPresenter.loadMeals(true);

        verify(mMealRepository).loadData(mLoadCallbackCaptor.capture());
        mLoadCallbackCaptor.getValue().onDataLoaded(ModelUtil.createStubMealsList());

        ArgumentCaptor<List> showMealsArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(mMealsFragment).showMeals(showMealsArgumentCaptor.capture());
        assertTrue(showMealsArgumentCaptor.getValue().size() == ModelUtil.LIST_SIZE);
    }

    @Test
    public void mealsUnavailable_showNoMeals(){
        mPresenter.loadMeals(true);

        verify(mMealRepository).loadData(mLoadCallbackCaptor.capture());
        mLoadCallbackCaptor.getValue().onDataNotAvailable();

        verify(mMealsFragment).showNoMeals();
    }

}
