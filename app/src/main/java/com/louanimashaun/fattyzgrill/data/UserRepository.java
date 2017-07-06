package com.louanimashaun.fattyzgrill.data;

import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.LinkedHashMap;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 30/06/2017.
 */

public class UserRepository implements DataSource<User> {


    private static UserRepository INSTANCE = null;

    private static UserLocalDataSource mLocalDataSource;

    private static UserRemoteDataSource mRemoteDataSource;

    private static boolean mIsCacheDirty = false;

    public static UserRepository getInstance(UserLocalDataSource localDataSource, UserRemoteDataSource remoteDataSource){
        if(INSTANCE == null){
            INSTANCE = new UserRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private UserRepository(UserLocalDataSource localDataSource, UserRemoteDataSource remoteDataSource){
        mLocalDataSource = checkNotNull(localDataSource);
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    @Override
    public void loadData(LoadCallback<User> callback) {

    }

    @Override
    public void getData(final GetCallback callback) {
        mLocalDataSource.getData(new GetCallback<User>() {
            @Override
            public void onDataLoaded(User data) {
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
            mRemoteDataSource.getData(new GetCallback<User>() {
                @Override
                public void onDataLoaded(User data) {
                    mLocalDataSource.saveData(data, null);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
            }
        });
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(User data, CompletionCallback callback) {

    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {

    }

    public void getUser(final String userID, final GetCallback<User> callback){

        //TODO add network configuration in
        if(mIsCacheDirty){
            getUserFromRemoteDataSource(userID, callback);
            mIsCacheDirty = false;
            return;
        }


        mLocalDataSource.getData(new GetCallback<User>() {
            @Override
            public void onDataLoaded(User data) {
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                 getUserFromRemoteDataSource(userID, callback);
            }
        });
    }


    private void getUserFromRemoteDataSource(String userID, final GetCallback callback){
        //TODO add network configuartion in
        mRemoteDataSource.getUser(userID, new GetCallback<User>() {

            @Override
            public void onDataLoaded(User data) {
                mLocalDataSource.saveData(data, null);
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }) ;
    }


    public void refreshData(){
        mIsCacheDirty = true;
    }
}
