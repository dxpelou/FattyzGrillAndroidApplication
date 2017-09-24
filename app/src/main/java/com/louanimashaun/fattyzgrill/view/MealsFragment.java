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
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.contract.BasePresenter;
import com.louanimashaun.fattyzgrill.contract.MealContract;
import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.ArrayList;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 *
 * View layer of application
 */

public class MealsFragment extends Fragment implements MealContract.View  {

    private BasePresenter mMealsPresenter;
    private MealsAdapter mMealsAdapter = new MealsAdapter(new ArrayList<Meal>());
    private RecyclerView.LayoutManager mLayoutManager;

    public static MealsFragment newInstance(){
        return new MealsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.meals_frag,container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.meals_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mMealsAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        mMealsPresenter.start();
    }

    @Override
    public void showMeals(List<Meal> meals) {
        checkNotNull(meals);
        mMealsAdapter.replaceData(meals);
    }

    @Override
    public void showNoMeals() {

    }

    @Override
    public void scrollToMealWithId(String id) {
        checkNotNull(id);
        int position = mMealsAdapter.getPositionById(id);

        if(position != -1){
            mLayoutManager.scrollToPosition(position);
        }
    }

    @Override
    public void setPresenter(@NonNull BasePresenter presenter) {
        mMealsPresenter = checkNotNull(presenter);
    }

    public void setMealClickListener(@NonNull Listeners.MealOnClickListener listener){
        checkNotNull(listener);
        mMealsAdapter.setItemClickListener(listener);
    }
}
