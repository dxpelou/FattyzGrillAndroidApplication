package com.louanimashaun.fattyzgrill.data;

import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
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

    private static DataSource mRemoteDataSource;

    public static UserRepository getInstance(UserLocalDataSource localDataSource, DataSource<User> remoteDataSource){
        if(INSTANCE == null){
            INSTANCE = new UserRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private UserRepository(UserLocalDataSource localDataSource, DataSource<User> remoteDataSource){
        mLocalDataSource = checkNotNull(localDataSource);
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    @Override
    public void loadData(LoadCallback<User> loadCallback) {

    }

    @Override
    public void getData(GetCallback getCallback) {

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
}
