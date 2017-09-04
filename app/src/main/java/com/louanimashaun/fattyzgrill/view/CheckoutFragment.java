package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.contract.BasePresenter;
import com.louanimashaun.fattyzgrill.contract.CheckoutContract;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 16/08/2017.
 */

public class CheckoutFragment extends Fragment implements CheckoutContract.View {

    private BasePresenter mCheckoutPresenter;

    private CheckoutAdapter mCheckoutAdapter;
    private TextView mTotalPrice_tv;


    public static CheckoutFragment newInstance(){
        return new CheckoutFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCheckoutPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mCheckoutAdapter = new CheckoutAdapter(getContext(), R.layout.item_meal,
                new ArrayList<Meal>());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_frag,container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.selected_meals_lv);

        Button orderButton = (Button) rootView.findViewById(R.id.order_button);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);
        mTotalPrice_tv = (TextView) rootView.findViewById(R.id.total_price_tv);

        listView.setAdapter(mCheckoutAdapter);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });

        return rootView;
    }

    @Override
    public void showCheckout(List<Meal> meals) {
        checkNotNull(meals);
        mCheckoutAdapter.refreshData(meals);
        showTotalPrice(meals);
    }

    @Override
    public void showNoCheckout() {
        showTotalPrice(new ArrayList<Meal>());
    }

    @Override
    public void notifyOrderSent() {
        //TODO
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        checkNotNull(presenter);
        mCheckoutPresenter = presenter;
    }

    private void showTotalPrice(List<Meal> meals){
        int total = 0;
        for(Meal meal : meals){
            total += meal.getPrice();
        }

        mTotalPrice_tv.setText("£ " + total);
    }

    private void sendOrder(){
        ((CheckoutPresenter)mCheckoutPresenter).checkoutOrder();
    }
}
