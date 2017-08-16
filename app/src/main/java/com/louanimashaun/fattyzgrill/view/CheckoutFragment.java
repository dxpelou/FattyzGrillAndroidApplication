package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.louanimashaun.fattyzgrill.R;

/**
 * Created by louanimashaun on 16/08/2017.
 */

public class CheckoutFragment extends Fragment {

    public static CheckoutFragment newInstance(){
        return new CheckoutFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.checkout_frag,container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
