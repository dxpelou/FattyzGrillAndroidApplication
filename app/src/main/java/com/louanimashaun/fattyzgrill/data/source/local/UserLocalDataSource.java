package com.louanimashaun.fattyzgrill.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.List;

/**
 * Created by louanimashaun on 03/07/2017.
 */

public class UserLocalDataSource implements DataSource<User> {

    private static UserLocalDataSource INSTANCE = null;
    private Context mContext;
    public static final String  APP_KEY = "com.louanimashaun.fattyzgrill";
    public static final String ADMIN_KEY = "is_admin_key";
    public static final String USER_KEY = "user_id";
;

    @Override
    public void loadData(LoadCallback<User> loadCallback) {

    }

    @Override
    public void getData(GetCallback<User> callback) {
        SharedPreferences sharedPref = mContext.getSharedPreferences(APP_KEY,Context.MODE_PRIVATE);
        String defaultValue = null;
        String userId = sharedPref.getString(USER_KEY, "");
        //TODO change isAdmin to enum
        boolean isAdmin = sharedPref.getBoolean(ADMIN_KEY, false);

        if(userId != "") {
            callback.onDataLoaded(new User(userId, isAdmin));
        }else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void deleteAll() {
        //not in use
    }

    @Override
    public void saveData(User data, @Nullable CompletionCallback callback) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE).edit();
        editor.putBoolean(ADMIN_KEY, data.isAdmin());
        editor.putString(USER_KEY, data.getUserId());
        editor.commit();
    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {

    }
}
