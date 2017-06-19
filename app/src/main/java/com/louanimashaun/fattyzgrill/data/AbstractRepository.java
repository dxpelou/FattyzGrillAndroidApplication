package com.louanimashaun.fattyzgrill.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by louanimashaun on 18/06/2017.
 */
 abstract  class AbstractRepository<T> implements DataSource<T>{

    protected static DataSource mLocalDataSource;

    protected static DataSource mRemoteDataSource;

    protected Map<Integer, T> mCachedData;

    private boolean mIsCacheDirty;

    @Override
    public void loadData(LoadCallBack<T> callBack) {
        if(!mIsCacheDirty && mCachedData != null ){
            callBack.LoadData(new ArrayList< >(mCachedData.values()));
            return;
        }

        if(mIsCacheDirty){
            loadDataFromRemoteDataSource(callBack);
            mIsCacheDirty = false;
            return;
        }
    }

    private void loadDataFromRemoteDataSource(final LoadCallBack<T> callBack) {
        mRemoteDataSource.loadData(new LoadCallBack<T>() {
            @Override
            public void LoadData(List<T> data) {
                refreshCache(data);
                refreshLocalDataSource(data);
                callBack.LoadData(data);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<T> data) {
        mLocalDataSource.deleteAll();

        for(T dataItem : data){
            mLocalDataSource.saveData(dataItem);
        }
    }

    abstract protected void refreshCache(List<T> data);

    abstract protected void putDataItemInCache(T data);

    @Override
     public void deleteAll() {
        //not in use
    }

    @Override
    public void saveData(T data) {
        putDataItemInCache(data);
        mLocalDataSource.saveData(data);
        mRemoteDataSource.saveData(data);
    }

    public void refreshData(){
        mIsCacheDirty = true;
    }
}