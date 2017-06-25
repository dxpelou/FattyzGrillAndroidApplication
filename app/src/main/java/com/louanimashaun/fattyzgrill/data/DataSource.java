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

    interface CompletionCallback{
        void onComplete();

        void onCancel();
    }

    void loadData(LoadCallBack<T> loadCallBack);

    void deleteAll();

    void saveData(T data, CompletionCallback callback);

    void saveData(List<T> data, CompletionCallback callback );
}
