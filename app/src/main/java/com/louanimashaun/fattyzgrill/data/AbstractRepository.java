package com.louanimashaun.fattyzgrill.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 18/06/2017.
 */
 public abstract  class AbstractRepository<T> implements DataSource<T>{

    protected DataSource mLocalDataSource;

    protected DataSource mRemoteDataSource;

    protected Map<String, T> mCachedData;

    private boolean mIsCacheDirty;

    @Override
    public void loadData(final LoadCallback<T> callBack) {
        /*if(!mIsCacheDirty && mCachedData != null ){
            callBack.onDataLoaded(new ArrayList< >(mCachedData.values()));
            return;
        }*/

        if(mIsCacheDirty){
            loadDataFromRemoteDataSource(callBack);
            mIsCacheDirty = false;
            return;
        }

        mLocalDataSource.loadData(new LoadCallback<T>() {
            @Override
            public void onDataLoaded(List<T> data) {
                //refreshCache(data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                loadDataFromRemoteDataSource(callBack);
            }
        });
    }

    @Override
    public void getData(final String id, final GetCallback<T> callback) {
        checkNotNull(callback);

        if(mIsCacheDirty){
          getDataFromRemoteDataSource(id, callback);
        }


        mLocalDataSource.getData(id, new GetCallback<T>() {
            @Override
            public void onDataLoaded(T data) {
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                getDataFromRemoteDataSource(id, callback);
            }
        });
    }

    private void loadDataFromRemoteDataSource(final LoadCallback<T> callBack) {
        mRemoteDataSource.loadData(new LoadCallback<T>() {
            @Override
            public void onDataLoaded(List<T> data) {
                //refreshCache(data);
                refreshLocalDataSource(data);
                callBack.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    private void getDataFromRemoteDataSource(String id, final GetCallback<T> callback){
        mRemoteDataSource.getData(id, new GetCallback<T>() {
            @Override
            public void onDataLoaded(T data) {
                //convert
                mLocalDataSource.saveData(data, null);
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<T> data) {
        mLocalDataSource.deleteAll();
        for(T dataItem : data){
            mLocalDataSource.saveData(dataItem, null);
        }
    }

    abstract protected void refreshCache(List<T> data);

    abstract protected void putDataItemInCache(T data);

    @Override
     public void deleteAll() {
        //not in use
    }

    @Override
    public void saveData(T data, CompletionCallback callback) {
        //possible convert
        mRemoteDataSource.saveData(data, callback);

        //possible convert
        mLocalDataSource.saveData(data, null);
    }




    @Override
    public void saveData(List<T> data, CompletionCallback callback) {
        refreshCache(data);
        mRemoteDataSource.saveData(data, callback);
        mLocalDataSource.saveData(data, null);
    }

    public void refreshData(){
        mIsCacheDirty = true;
    }
}
