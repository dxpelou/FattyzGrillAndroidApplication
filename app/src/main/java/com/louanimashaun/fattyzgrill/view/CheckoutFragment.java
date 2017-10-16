package com.louanimashaun.fattyzgrill.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 16/08/2017.
 */

@ActivityScoped
public class CheckoutFragment extends DaggerFragment implements CheckoutContract.View {

    @Inject
    public CheckoutContract.Presenter mCheckoutPresenter;

    private CheckoutAdapter mCheckoutAdapter;
    private TextView mTotalPrice_tv;

    @Inject
    public CheckoutFragment(){}

    public static CheckoutFragment newInstance(){
        return new CheckoutFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCheckoutPresenter.takeView(this);
        mCheckoutPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mCheckoutAdapter = new CheckoutAdapter(getContext(), R.layout.item_checkout_meal,
                new ArrayList<Meal>());

        mCheckoutAdapter.setIncrButtonListener(new Listeners.CheckoutItemClickListener() {
            @Override
            public void onClick(String mealdID, boolean isUp) {
                mCheckoutPresenter.changeQuantity(mealdID, true);
            }
        });

        mCheckoutAdapter.setDecButtonListener(new Listeners.CheckoutItemClickListener() {
            @Override
            public void onClick(String mealdID, boolean isUp) {
                mCheckoutPresenter.changeQuantity(mealdID, false);

            }
        });
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
    public void showCheckout(List<Meal> meals, List<Integer> quantities) {
        checkNotNull(meals);
        checkNotNull(quantities);
        mCheckoutAdapter.refreshData(meals, quantities);
        showTotalPrice(meals);
    }

    @Override
    public void showNoCheckout() {
        showTotalPrice(new ArrayList<Meal>());
    }

    @Override
    public void notifyOrderSent() {
        showAlertDialog("Order Sent");
    }

    @Override
    public void notifyOrderError() {
        showAlertDialog("Order Failed");
    }

    @Override
    public void showQuantityChanged() {
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


    private void showAlertDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
