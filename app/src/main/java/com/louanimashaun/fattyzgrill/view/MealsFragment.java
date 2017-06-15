package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louanimashaun.fattyzgrill.MealsContract;
import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * View layer of application
 */

public class MealsFragment extends Fragment implements MealsContract.View  {

    private MealsContract.Presenter mMealsPresenter;
    private MealsAdapter mMealsAdapter;

    public static MealsFragment newInstance(){
        return new MealsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mMealsAdapter = new MealsAdapter(new ArrayList<Meal>());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meals_frag,container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.meals_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMealsAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMealsPresenter.start();
    }

    @Override
    public void showMeals(List<Meal> meals) {

    }

    @Override
    public void showNoMeals() {

    }

    @Override
    public void setPresenter(@NonNull MealsContract.Presenter presenter) {
        mMealsPresenter = checkNotNull(presenter);
    }
}
