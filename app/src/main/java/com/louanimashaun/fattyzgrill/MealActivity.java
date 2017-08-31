package com.louanimashaun.fattyzgrill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealRepository;
import com.louanimashaun.fattyzgrill.data.OrderRepository;
import com.louanimashaun.fattyzgrill.data.UserRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSoure;
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.OrdersRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.User;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.presenter.CheckoutPresenter;
import com.louanimashaun.fattyzgrill.util.AdminUtil;
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;
import com.louanimashaun.fattyzgrill.view.MealOnClickListener;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealActivity extends AppCompatActivity {

    private MealsPresenter mMealsPresenter;
    FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    private UserRepository mUserRepository;
    private MealRepository mMealRepository;
    private OrderRepository mOrderRepository;
    private List<String> mSelectedMealIDs;
    private MealOnClickListener mMealOnClickListener;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MealsFragment mealsFragment = MealsFragment.newInstance();
                    replaceFragment(mealsFragment);

                    mealsFragment.setMealClickListener(mMealOnClickListener);

                    MealsPresenter mealsPresenter = new MealsPresenter(
                            mMealRepository,
                            mealsFragment);

                    mealsFragment.setPresenter(mealsPresenter);

                    return true;
                case R.id.navigation_checkout:
                    CheckoutFragment checkoutFragment = CheckoutFragment.newInstance();
                    replaceFragment(checkoutFragment);

                    CheckoutPresenter checkoutPresenter = new CheckoutPresenter(mOrderRepository, mMealRepository, checkoutFragment);
                    checkoutPresenter.addSelectedMeals(mSelectedMealIDs);
                    checkoutFragment.setPresenter(checkoutPresenter);
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

        if(AdminUtil.isAdmin()) {
            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.order_FCM_topic));
        }

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

        setUpRepositories();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMealOnClickListener = new MealOnClickListener() {
            @Override
            public void onClick(String mealID) {
                if(mSelectedMealIDs == null)
                    mSelectedMealIDs = new ArrayList<>();

                mSelectedMealIDs.add(mealID);
            }
        };

        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        mealsFragment.setMealClickListener(mMealOnClickListener);

        commitFragmentTransaction(R.id.content_frame, mealsFragment);

        final MealsPresenter mMealsPresenter = new MealsPresenter(
                mMealRepository,
                mealsFragment);

        String[] meals = {"chicken", "chips"};

       AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.auto_complete_tv);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meals));

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
                            Log.d("hi","hi");
                        }

                        @Override
                        public void onCancel() {
                            Log.d("hi","hi");
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }


    private void setUpRepositories(){
        mUserRepository = UserRepository.getInstance(UserLocalDataSource.getInstance(this),
                UserRemoteDataSource.getInstance());

        mOrderRepository = OrderRepository.getInstance(
                OrdersLocalDataSource.getInstance(),
                OrdersRemoteDataSource.getInstance());

        mMealRepository = MealRepository.getInstance(
                MealsLocalDataSoure.getInstance(),
                MealsRemoteDataSource.getInstance());
    }


}
