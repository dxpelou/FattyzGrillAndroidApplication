package com.louanimashaun.fattyzgrill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.louanimashaun.fattyzgrill.view.CheckoutFragment;
import com.louanimashaun.fattyzgrill.view.Listeners.MealOnClickListener;
import com.louanimashaun.fattyzgrill.view.MealsFragment;
import com.louanimashaun.fattyzgrill.view.NotificationFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MealActivity extends DaggerAppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    private UserRepository mUserRepository;

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

    public MealRepository mMealRepository;
    private OrderRepository mOrderRepository;
    private List<String> mSelectedMealIDs;
    private Map<String,Integer> mIdQuantityMap;
    private MealOnClickListener mMealOnClickListener;

    private String[] ids;


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
                    return true;
                case R.id.navigation_notifications:

                    replaceFragment(mNotificationFragment);

                    NotificationLocalDataSource notificationLocalDataSource = NotificationLocalDataSource.getInstance();


//                    notificationFragment.setPresenter(notificationPresenter);
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
            public void onClick(String mealId) {
                if(mIdQuantityMap == null) mIdQuantityMap = new HashMap<String, Integer>();

                if(!mIdQuantityMap.containsKey(mealId)){
                    mIdQuantityMap.put(mealId, 1);
                    return;
                }
                int quantity = mIdQuantityMap.get(mealId);
                mIdQuantityMap.put(mealId, quantity++);

            }
        };

        //TODO app crashes when using instant run
        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        mealsFragment.setMealClickListener(mMealOnClickListener);

        commitFragmentTransaction(R.id.content_frame, mealsFragment);

//         MealsPresenter mMealsPresenter = new MealsPresenter(
//                mMealRepository,
//                mealsFragment);

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
                MealsLocalDataSource.getInstance(),
                MealsRemoteDataSource.getInstance());
    }


    private void setupAutoCompleteTextView(){
        mMealRepository.loadData(new DataSource.LoadCallback<Meal>() {
            @Override
            public void onDataLoaded(List<Meal> data) {
                Map<String, String> idTitleMap = ModelUtil.convertToTitleIdMap(data);
                String[] meals = idTitleMap.values().toArray(new String[data.size()]);

                ids = idTitleMap.keySet().toArray(new String[data.size()]);

                AutoCompleteTextView autoCompleteTextView =
                        (AutoCompleteTextView)findViewById(R.id.auto_complete_tv);
                autoCompleteTextView.setAdapter(
                        new ArrayAdapter<>(MealActivity.this, android.R.layout.simple_list_item_1, meals));

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Toast.makeText(MealActivity.this, "click listener", Toast.LENGTH_SHORT).show();

                        mMealsPresenter.findMeal(ids[i]);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
