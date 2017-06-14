package com.louanimashaun.fattyzgrill.presenter;

import android.support.annotation.NonNull;

import com.louanimashaun.fattyzgrill.MenuContract;
import com.louanimashaun.fattyzgrill.data.MealsRepository;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *Presenter layer of application
 */

public class MealsPresenter implements MenuContract.Presenter{

    private final MealsRepository mMealsRepository;
    private final MealsFragment mMealsView;


    public MealsPresenter(@NonNull MealsRepository repository, MealsFragment mealsView){
        mMealsRepository = checkNotNull(repository);
        mMealsView = checkNotNull(mealsView);
        mMealsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMenuItems() {

    }
}
