package com.louanimashaun.fattyzgrill.data;

import java.util.List;

/**
 * Defines the contract all DataSources follow
 */

public interface DataSource <T> {

    interface LoadCallback<T>{
        void onDataLoaded(List<T> data);

        void onDataNotAvailable();
    }

    interface GetCallback<T>{
        void onDataLoaded(T data);

        void onDataNotAvailable();
    }

    interface CompletionCallback{
        void onComplete();

        void onCancel();
    }

    void loadData(LoadCallback<T> loadCallback);

    void getData(GetCallback getCallback);

    void deleteAll();

    void saveData(T data, CompletionCallback callback);

    void saveData(List<T> data, CompletionCallback callback );
}
