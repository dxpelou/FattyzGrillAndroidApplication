package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.louanimashaun.fattyzgrill.MealsContract;
import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.OrderBuilder;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 21/06/2017.
 */

public class CheckoutDialogFragment extends BottomSheetDialogFragment {

    private List<Meal> mSelectedMeals;
    private MealsContract.Presenter mPresenter;

    public static CheckoutDialogFragment newInstance(@NonNull List<Meal> selectedMeals, @NonNull MealsContract.Presenter presenter){
        CheckoutDialogFragment checkoutDialogFragment = new CheckoutDialogFragment();
        checkoutDialogFragment.mSelectedMeals = checkNotNull(selectedMeals);
        checkoutDialogFragment.mPresenter = checkNotNull(presenter);
        return checkoutDialogFragment;
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

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPresenter.orderCompleted(OrderBuilder.build(mSelectedMeals));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

}