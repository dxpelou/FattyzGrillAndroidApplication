package com.louanimashaun.fattyzgrill;

import android.content.Context;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSoure;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by louanimashaun on 26/08/2017.
 */

public class RepositoryTest {

    private MealRepository mMealRepository;

    @Mock
    private MealsLocalDataSoure mMealsLocalDataSource;

    @Mock
    private MealsRemoteDataSource mMealsRemoteDataSource;

    @Mock
    private DataSource.LoadCallback mLoadCallback;

    @Mock
    private DataSource.GetCallback mGetCallback;

    @Mock
    private Context mContext;

    @Captor
    private ArgumentCaptor<DataSource.LoadCallback> mLoadCallbackCaptor;


    private String MEAL_ID = "0000";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mMealRepository
               =  MealRepository.getInstance(mMealsLocalDataSource, mMealsRemoteDataSource);
    }

    @After
    public void destroyRepositoryInstance(){
        MealRepository.destroyInstance();
    }

    @Test
    public void loadData_loadsdataFromLocalDataSource(){
        mMealRepository.loadData(mLoadCallback);

        verify(mMealsLocalDataSource).loadData(any(DataSource.LoadCallback.class));
    }

    //TODO @TEST implement getData in abtract repository
    public void getData_getsDataFromLocalDataSource(){
        mMealRepository.getData(MEAL_ID, mGetCallback);

        verify(mMealsLocalDataSource).getData(eq(MEAL_ID), any(DataSource.GetCallback.class));
    }

    @Test
    public void refreshData_loadDataFromRemote(){
        mMealRepository.refreshData();
        mMealRepository.loadData(mLoadCallback);

        verify(mMealsRemoteDataSource).loadData(any(DataSource.LoadCallback.class));
    }

    @Test
    public void remoteUnavailable_loadFromLocal(){
        mMealRepository.loadData(mLoadCallback);

        verify(mMealsLocalDataSource).loadData(mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataNotAvailable();

        verify(mMealsRemoteDataSource).loadData(any(DataSource.LoadCallback.class));
    }

    @Test
    public void bothDataSourcesUnavailable_firesOnDataUnavailable(){
        mMealRepository.loadData(mLoadCallback);

        verify(mMealsLocalDataSource).loadData(mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataNotAvailable();

        verify(mMealsRemoteDataSource).loadData(mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataNotAvailable();

        verify(mLoadCallback).onDataNotAvailable();

    }

    @Test
    public void loadDataById_loadsDataFromLocalDataSource(){
        mMealRepository.loadDataByIds(ModelUtil.createStubMealIDList(), mLoadCallback);

        verify(mMealsLocalDataSource).loadDataByIds(eq(ModelUtil.createStubMealIDList()), mLoadCallbackCaptor.capture());

    }


    @Test
    public void loadDataById_CacheIsDirty_LoadsFromRemote(){
        mMealRepository.refreshData();

        mMealRepository.loadDataByIds(ModelUtil.createStubMealIDList(), mLoadCallback);

        verify(mMealsRemoteDataSource).loadDataByIds(eq(ModelUtil.createStubMealIDList()),
                any(DataSource.LoadCallback.class));
    }


    public void loadDataByIds_LocalDataSourceUnavailable_LoadFromRemote(){
        mMealRepository.loadDataByIds(ModelUtil.createStubMealIDList(), mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().onDataNotAvailable();

        verify(mMealsRemoteDataSource).loadDataByIds(eq(ModelUtil.createStubMealIDList()),
                any(DataSource.LoadCallback.class));
    }

}


































