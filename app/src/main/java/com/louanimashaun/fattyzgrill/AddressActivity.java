package com.louanimashaun.fattyzgrill;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import dagger.android.support.DaggerAppCompatActivity;

public class AddressActivity extends DaggerAppCompatActivity {

    private EditText mFirstLine;
    private EditText mSecondLine;
    private EditText mCity;
    private EditText mPostCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkoutOrder();
            }
        });

        mFirstLine = (EditText) findViewById(R.id.address_first_line_et);
        mSecondLine = (EditText) findViewById(R.id.address_second_line_et);
        mCity = (EditText) findViewById(R.id.city_et);
        mPostCode = (EditText) findViewById(R.id.postcode_et);
    }

    private String getOrderId(){
        Bundle bundle = getIntent().getExtras();
        String id = "";

        if(bundle != null && bundle.getString("orderId") != null) {
            id = bundle.getString("orderId");
        }else{
            id = null;
        }

        return id;
    }


    private void checkoutOrder(){
        String orderId = getOrderId();

    }

}
