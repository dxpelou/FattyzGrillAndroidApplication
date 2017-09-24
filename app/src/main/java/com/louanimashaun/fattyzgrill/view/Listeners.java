package com.louanimashaun.fattyzgrill.view;

/**
 * Created by louanimashaun on 28/08/2017.
 */


public interface Listeners{

    interface MealOnClickListener {

        void  onClick(String mealID);
    }

    interface CheckoutItemClickListener{

        void onClick(String mealdID, boolean isUp );
    }

}

