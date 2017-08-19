package com.louanimashaun.fattyzgrill.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.louanimashaun.fattyzgrill.MealsContract;
import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.util.ModelUtil;

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
        setHasOptionsMenu(true);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMealsAdapter);
        recyclerView.setHasFixedSize(true);

       /* recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int space = 1;
                outRect.left = space;
                outRect.right = space;
                outRect.bottom = space;

                if(parent.getChildAdapterPosition(view) == 0){
                    outRect.top = space;
                }
            }
        });*/
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_checkout){
            CheckoutDialogFragment.newInstance(ModelUtil.createStubMealsList(), mMealsPresenter).show(getFragmentManager(),"MealsFragment");
        }
        return super.onOptionsItemSelected(item);
    }*/

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
    public void setPresenter(@NonNull MealsContract.Presenter presenter) {
        mMealsPresenter = checkNotNull(presenter);
    }

}
