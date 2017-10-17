package com.louanimashaun.fattyzgrill.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 17/10/2017.
 */

public class OrdersDialogFragment extends DialogFragment {

    private static List<Meal> mMeals;
    private static List<Integer> mQuantities;
    private ArrayAdapter mArrayAdapter;


    public static OrdersDialogFragment getNewInstance(List<Meal> meals, List<Integer> quantities){
        mMeals = checkNotNull(meals);
        mQuantities = checkNotNull(quantities);


        return new OrdersDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("HEllo")
                .setItems(createArray(), null)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }

    private String[] createArray(){
        String[] array = new String[mMeals.size()];
        for(int i =0; i < mMeals.size(); i ++){
            String item = mMeals.get(i).getTitle() + "   x" + mQuantities.get(i).toString();
            array[i] = item;
        }

        return array;
    }
}
