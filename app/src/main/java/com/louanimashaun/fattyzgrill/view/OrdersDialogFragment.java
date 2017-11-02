package com.louanimashaun.fattyzgrill.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Order;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 17/10/2017.
 */

public class OrdersDialogFragment extends DialogFragment {

    private static List<Meal> mMeals;
    private static Order mOrder;
    private ArrayAdapter mArrayAdapter;
    private Listeners.AcceptOrderClickListener mAcceptClickListener;
    private DialogInterface.OnClickListener mCancelClickListener;


    public static OrdersDialogFragment getNewInstance(List<Meal> meals, Order order){
        mMeals = checkNotNull(meals);
        mOrder = checkNotNull(order);

        return new OrdersDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Order")
                .setItems(createArray(), null)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mAcceptClickListener != null ){
                            mAcceptClickListener.onClick(mOrder.getId());
                            //getDialog().cancel();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mCancelClickListener != null){
                            //getDialog().dismiss();
                            //mCancelClickListener.onClick(dialog, id);
                        }
                    }
                });

        return builder.create();
    }

    private String[] createArray(){
        String[] array = new String[mMeals.size()];
        for(int i =0; i < mMeals.size(); i ++){
            String item = mMeals.get(i).getTitle() + "   x" + mOrder.getQuantities().get(i).toString();
            array[i] = item;
        }

        return array;
    }

    public void setAcceptClickListener(Listeners.AcceptOrderClickListener acceptClickListener) {
        mAcceptClickListener = checkNotNull(acceptClickListener);
    }

}
