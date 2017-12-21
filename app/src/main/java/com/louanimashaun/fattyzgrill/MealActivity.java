package com.louanimashaun.fattyzgrill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.data.UserRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.NotificationLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.OrdersRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.User;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;
import com.louanimashaun.fattyzgrill.presenter.NotificationPresenter;
import com.louanimashaun.fattyzgrill.util.AdminUtil;
import com.louanimashaun.fattyzgrill.util.ModelUtil;
import com.louanimashaun.fattyzgrill.util.Util;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;
import com.louanimashaun.fattyzgrill.view.Listeners;
import com.louanimashaun.fattyzgrill.view.Listeners.MealOnClickListener;
import com.louanimashaun.fattyzgrill.view.MealsFragment;
import com.louanimashaun.fattyzgrill.view.NotificationFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MealActivity extends DaggerAppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    static final String TAG = "MealActivity";


    @Inject
    MealsPresenter mMealsPresenter;

    @Inject
    MealsFragment mealsFragment;

    @Inject
    CheckoutFragment mCheckoutFragment;

    @Inject
    CheckoutPresenter mCheckoutPresenter;

    @Inject
    NotificationFragment mNotificationFragment;

    @Inject
    NotificationPresenter mNotificationPresenter;

    @Inject
    MealRepository mMealRepository;

    @Inject
    OrderRepository mOrderRepository;

    @Inject
    public UserRepository mUserRepository;

    private List<String> mSelectedMealIDs;
    private Map<String,Integer> mIdQuantityMap;
    private MealOnClickListener mMealOnClickListener;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(mealsFragment);

                    mealsFragment.setMealClickListener(mMealOnClickListener);

                    return true;
                case R.id.navigation_checkout:
                    replaceFragment(mCheckoutFragment);

                    mCheckoutPresenter.addSelectedMeals(mIdQuantityMap);
                    mCheckoutPresenter.addCheckoutChangeListener(new Listeners.CheckoutChangedListener() {
                        @Override
                        public void onCheckoutChanged(Map<String, Integer> quanityMap) {
                            mIdQuantityMap = quanityMap;
                            updateBasketCount();
                        }
                    });
                    return true;
                case R.id.navigation_notifications:

                    replaceFragment(mNotificationFragment);

                    //NotificationLocalDataSource notificationLocalDataSource = NotificationLocalDataSource.getInstance();
                    //notificationFragment.setPresenter(notificationPresenter);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        //FirebaseMessaging.getInstance().unsubscribeFromTopic(getString(R.string.order_FCM_topic));

        setContentView(R.layout.meals_act2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    getUser(user);

                }else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false).setProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
//                                    //,new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);


                    //needed
//                                    .setAvailableProviders(
//                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
//                                    //,new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
//                                    .build(),
//                            RC_SIGN_IN);
                }
            }
        };

        if(AdminUtil.isAdmin()) {
            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.order_FCM_topic));
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMealOnClickListener = new MealOnClickListener() {
            @Override
            public void onClick(String mealId) {
                //must be a LinkedHashMap to preserve order and for CheckoutPresenterUnitTest
                if(mIdQuantityMap == null) mIdQuantityMap = new LinkedHashMap<String, Integer>();

                if(!mIdQuantityMap.containsKey(mealId)){
                    mIdQuantityMap.put(mealId, 1);
                    updateBasketCount();
                    return;
                }
                int quantity = mIdQuantityMap.get(mealId);
                mIdQuantityMap.put(mealId, quantity + 1);

                updateBasketCount();

                Toast.makeText(Util.getApp(), "Item added", Toast.LENGTH_SHORT).show();
            }
        };

        //TODO app crashes when using instant run
        //TODO
        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        mealsFragment.setMealClickListener(mMealOnClickListener);
        commitFragmentTransaction(R.id.content_frame, mealsFragment);

        setupAutoCompleteTextView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }


    public void getUser(final FirebaseUser firebaseUser){
        //mUser Repository should be implemented through
        // presenter to increase testability

        mUserRepository.refreshData();

        mUserRepository.getUser(firebaseUser.getUid(), new DataSource.GetCallback<User>() {
            @Override
            public void onDataLoaded(User data) {
            //only call get user to make sure, the user data is saved to remote date source
                Toast.makeText(Util.getApp(), "Login Successful", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void onDataNotAvailable() {


            }
        }, new DataSource.ErrorCallback() {
            @Override
            public void onError(int errorCode) {
                if (errorCode == UserRemoteDataSource.UserNotFoundErrorCode) {
                    User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail() ,false);
                    mUserRepository.saveData(user, new DataSource.CompletionCallback() {
                        @Override
                        public void onComplete() {
                            Toast.makeText(Util.getApp(), "Login Successful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        });
    }

    private void commitFragmentTransaction(int fragmentId, Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(fragmentId, fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        if(fragment.isAdded()) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void setupAutoCompleteTextView(){
        mMealRepository.loadData(new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {

                final Map<String, String> idTitleMap = ModelUtil.convertToTitleIdMap(data);
                String[] meals = idTitleMap.values().toArray(new String[data.size()]);

                final List<String> mealTitles = new ArrayList<String>(idTitleMap.values());
                final List<String> ids = new ArrayList<String>(idTitleMap.keySet());

                //ids = idTitleMap.keySet().toArray(new String[data.size()]);

                AutoCompleteTextView autoCompleteTextView =
                        (AutoCompleteTextView)findViewById(R.id.auto_complete_tv);

                autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        return false;
                    }
                });
                autoCompleteTextView.setAdapter(
                        new ArrayAdapter<>(MealActivity.this, android.R.layout.simple_list_item_1, mealTitles));

                //autoCompleteTextView.setAdapter(new SimpleAdapter(Util.getApp(), idTitleMap, android.R.layout.simple_list_item_1, meals, ));

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String chosenMeal = (String) adapterView.getAdapter().getItem(i);
                        int index = mealTitles.indexOf(chosenMeal);
                        mMealsPresenter.findMeal(ids.get(index));
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateBasketCount(){
        TextView basket_tv = (TextView) findViewById(R.id.basket_quantity);
        Collection<Integer> quantities = mIdQuantityMap.values();
        int total = 0;

        for(Integer quant : quantities){
            total += quant;
        }

        basket_tv.setText(Integer.toString(total));
    }


    public class MealHolder{
        String id;
        String title;
    }

}
