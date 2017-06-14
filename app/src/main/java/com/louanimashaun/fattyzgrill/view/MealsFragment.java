package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.louanimashaun.fattyzgrill.MenuContract;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * View layer of architecture
 */

public class MealsFragment extends Fragment implements MenuContract.View  {



    public static MealsFragment newInstance(){
        return new MealsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void showMenuItems() {

    }

    @Override
    public void showNoMenuItems() {

    }

    @Override
    public void setPresenter(MenuContract.Presenter presenter) {

    }
}
