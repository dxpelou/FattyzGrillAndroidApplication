package com.louanimashaun.fattyzgrill;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by louanimashaun on 21/09/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<MealActivity> mActivityRule = new ActivityTestRule<>(
            MealActivity.class);




    @Test
    public void run(){
        Espresso.registerIdlingResources(MealsPresenter.idlingResource);
        //TODO sometimes fails use idling resoure framework
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.meals_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToCheckout(){
        onView(withId(R.id.navigation_checkout)).perform(click());

        onView(withId(R.id.checkout_frag)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToNotifications(){
        onView(withId(R.id.navigation_notifications)).perform(click());

        onView(withId(R.id.notification_frag)).check(matches(isDisplayed()));
    }

    public void basketCount(){
        //may need to user recyler view espresso framework to select item by value or use any

        Random rnd = new Random();
        int clickNumb = rnd.nextInt(10);
        int count = 0;

        onView(withId(R.id.basket_count)).check(matches(isDisplayed()));

        for(int i = 0; i < clickNumb; i++ ) {
            onView(withId(R.id.meal_item)).perform(click());

            onView(ViewMatchers.withId(R.id.meals_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        }
    }

}
