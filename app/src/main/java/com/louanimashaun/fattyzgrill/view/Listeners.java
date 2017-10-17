package com.louanimashaun.fattyzgrill.view;

import java.util.Map;

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

    interface CheckoutChangedListener{
        void onCheckoutChanged(Map<String, Integer> quanityMap);
    }

    interface NotificationOnClickListener{
        void onClick(String notificationId);
    }

}

