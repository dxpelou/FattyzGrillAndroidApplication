package com.louanimashaun.fattyzgrill;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by louanimashaun on 05/10/2017.
 */

public class AddItemViewAction {

    public static ViewAction clickChildViewAction(final int id){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);

                v.performClick();
            }
        };
    }

}
