package com.louanimashaun.fattyzgrill.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.List;

/**
 * Created by louanimashaun on 21/06/2017.
 */

public class CheckoutDialogFragment extends BottomSheetDialogFragment {

    private List<Meal> selectedMeals;

    public static CheckoutDialogFragment newInstance(List<Meal> selectedMeals){
        return new CheckoutDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}