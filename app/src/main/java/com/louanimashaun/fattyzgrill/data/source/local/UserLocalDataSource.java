package com.louanimashaun.fattyzgrill.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.List;

/**
 * Created by louanimashaun on 03/07/2017.
 */

public class UserLocalDataSource implements DataSource<User> {

    private static UserLocalDataSource INSTANCE = null;
    private Context mContext;
    String key = "com.example.myapp.PREFERENCE_FILE_KEY";


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
        SharedPreferences.Editor sharedPrefEditor = mContext.getSharedPreferences("KEY",Context.MODE_PRIVATE).edit();
        sharedPrefEditor.putBoolean("KEY",true);
        sharedPrefEditor.commit();

        //TODO save user id and admin status

    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {

    }
}
