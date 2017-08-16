package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.util.ModelUtil;

import java.util.List;

/**
 * Created by louanimashaun on 16/08/2017.
 */

public class CheckoutFragment extends Fragment {

    private static List<Meal> mSelectedMeals;

    public static CheckoutFragment newInstance(){
        mSelectedMeals = ModelUtil.createStubMealsList();
        return new CheckoutFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_frag,container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.selected_meals_lv);
        Button orderButton = (Button) rootView.findViewById(R.id.order_button);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);

        listView.setAdapter(new CheckoutAdapter(getContext(), R.layout.item_meal,
                mSelectedMeals));

        return rootView;
    }
}
