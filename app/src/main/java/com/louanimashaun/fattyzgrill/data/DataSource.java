package com.louanimashaun.fattyzgrill.data;

import java.util.List;

/**
 * Defines the contract all DataSources follow
 */

public interface DataSource <T> {

    interface LoadCallBack<T>{
        void LoadData(List<T> data);

        void onDataNotAvailable();
    }

    void loadData(LoadCallBack<T> loadCallBack);

    void deleteAll();

    void saveData(T data);
}
