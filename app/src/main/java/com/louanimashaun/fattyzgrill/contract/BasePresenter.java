package com.louanimashaun.fattyzgrill.contract;

/**
 * Created by louanimashaun on 27/08/2017.
 */

public interface BasePresenter<T extends BaseView> {
    void start();

    void takeView(T view);
}
