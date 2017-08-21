package com.louanimashaun.fattyzgrill.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.louanimashaun.fattyzgrill.MealsActivity;
import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealsRepository;
import com.louanimashaun.fattyzgrill.data.OrdersRepository;
import com.louanimashaun.fattyzgrill.data.UserRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSoure;
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.OrdersRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.User;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;

import java.util.Arrays;

public class MealActivity2 extends AppCompatActivity {

    private MealsPresenter mMealsPresenter;
    FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    private UserRepository mUserRepository;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MealsFragment mealsFragment = MealsFragment.newInstance();
                    replaceFragment(mealsFragment);

                    MealsRepository mealsRepository = MealsRepository.getInstance(
                            MealsLocalDataSoure.getInstance(),
                            MealsRemoteDataSource.getInstance());

                    OrdersRepository ordersRepository = OrdersRepository.getInstance(
                            OrdersLocalDataSource.getInstance(),
                            OrdersRemoteDataSource.getInstance());

                    MealsPresenter mMealsPresenter = new MealsPresenter(
                            mealsRepository,
                            ordersRepository,
                            mealsFragment);

                    return true;
                case R.id.navigation_dashboard:
                    CheckoutFragment checkoutFragment = CheckoutFragment.newInstance();
                    replaceFragment(checkoutFragment);
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
        setContentView(R.layout.meals_act2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mUserRepository = UserRepository.getInstance(UserLocalDataSource.getInstance(this),
                UserRemoteDataSource.getInstance());

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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        commitFragmentTransaction(R.id.content_frame, mealsFragment);

        MealsRepository mealsRepository = MealsRepository.getInstance(
                MealsLocalDataSoure.getInstance(),
                MealsRemoteDataSource.getInstance());

        OrdersRepository ordersRepository = OrdersRepository.getInstance(
                OrdersLocalDataSource.getInstance(),
                OrdersRemoteDataSource.getInstance());

        MealsPresenter mMealsPresenter = new MealsPresenter(
                mealsRepository,
                ordersRepository,
                mealsFragment);

        String[] meals = {"chicken", "chips"};

       AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.auto_complete_tv);
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meals));
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setIconifiedByDefault(false);
//
//
//
//        return true;
//    }

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
                    mUserRepository.saveData(user, null);
                }
            }
        });
    }

}
