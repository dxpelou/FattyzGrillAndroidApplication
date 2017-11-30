package com.louanimashaun.fattyzgrill;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

public class MealActivity extends DaggerAppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;


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
    private Map<String, Integer> mIdQuantityMap;
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
                if (user != null) {

                    getUser(user);

                } else {
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

        if (AdminUtil.isAdmin()) {
            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.order_FCM_topic));
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mMealOnClickListener = new MealOnClickListener() {
            @Override
            public void onClick(String mealId) {
                //must be a LinkedHashMap to preserve order and for CheckoutPresenterUnitTest
                if (mIdQuantityMap == null) mIdQuantityMap = new LinkedHashMap<String, Integer>();

                if (!mIdQuantityMap.containsKey(mealId)) {
                    mIdQuantityMap.put(mealId, 1);
                    updateBasketCount();
                    return;
                }
                int quantity = mIdQuantityMap.get(mealId);
                mIdQuantityMap.put(mealId, quantity + 1);

                updateBasketCount();
            }
        };

        //TODO app crashes when using instant run
        //TODO
        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null) {
            mealsFragment = MealsFragment.newInstance();
        }

        mealsFragment.setMealClickListener(mMealOnClickListener);
        commitFragmentTransaction(R.id.content_frame, mealsFragment);

        setupAutoCompleteTextView();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();





    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void getUser(final FirebaseUser firebaseUser) {
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
                    User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), false);
                    mUserRepository.saveData(user, new DataSource.CompletionCallback() {
                        @Override
                        public void onComplete() {
                            Log.d("hi", "hi");
                        }

                        @Override
                        public void onCancel() {
                            Log.d("hi", "hi");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if ( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    mLocationRequest = LocationRequest.create();
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    mLocationRequest.setInterval(1000);

                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
                        Toast.makeText(MealActivity.this,"pressed",Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
                autoCompleteTextView.setAdapter(
                        new ArrayAdapter<>(MealActivity.this, android.R.layout.simple_list_item_1, mealTitles));

                //autoCompleteTextView.setAdapter(new SimpleAdapter(Util.getApp(), idTitleMap, android.R.layout.simple_list_item_1, meals, ));

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(MealActivity.this, "click listener", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "location updated" + location.getLongitude(), Toast.LENGTH_LONG );
    }


    public class MealHolder{
        String id;
        String title;
    }

}
