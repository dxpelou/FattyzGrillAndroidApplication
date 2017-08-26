package com.louanimashaun.fattyzgrill.data;

import android.util.Log;
import android.widget.Toast;

import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.User;
import com.louanimashaun.fattyzgrill.util.AdminUtil;

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

    private static final String TAG = "UserRepositoty";

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
    public void getData(String id, final GetCallback callback) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveData(User data, CompletionCallback callback) {
         mRemoteDataSource.saveData(data, callback);

        mLocalDataSource.saveData(data, callback);
    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {

    }

    public void getUser(final String userId, final GetCallback<User> callback, final ErrorCallback errorCallback){

        //TODO add network configuration in
        if(mIsCacheDirty){
            getUserFromRemoteDataSource(userId, callback, errorCallback);
            mIsCacheDirty = false;
            return;
        }


        mLocalDataSource.getData(userId, new GetCallback<User>() {
            @Override
            public void onDataLoaded(User data) {
                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                 getUserFromRemoteDataSource(userId, callback, errorCallback);
            }
        });
    }


    private void getUserFromRemoteDataSource(String userId, final GetCallback callback, ErrorCallback errorCallback){
        //TODO add network configuartion in
        mRemoteDataSource.getUser(userId, new GetCallback<User>() {

            @Override
            public void onDataLoaded(User data) {
                mLocalDataSource.saveData(data, null);
                AdminUtil.setAdminStatus(data.isAdmin());

                callback.onDataLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }, errorCallback) ;
    }


    public void refreshData(){
        mIsCacheDirty = true;
    }
}
