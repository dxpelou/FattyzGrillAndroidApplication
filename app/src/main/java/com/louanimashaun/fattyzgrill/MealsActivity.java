package com.louanimashaun.fattyzgrill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.data.MealsRepository;
import com.louanimashaun.fattyzgrill.data.OrdersRepository;
import com.louanimashaun.fattyzgrill.data.UserRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSoure;
import com.louanimashaun.fattyzgrill.Notifications.NotificationSharedPreference;
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.local.UserLocalDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.OrdersRemoteDataSource;
import com.louanimashaun.fattyzgrill.data.source.remote.UserRemoteDataSource;
import com.louanimashaun.fattyzgrill.model.User;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import java.util.Arrays;

public class MealsActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final String ACCOUNT_TYPE = "fattyz_mobile_app";
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "MealsActivity";

    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        NotificationSharedPreference.init(this);
        setContentView(R.layout.meals_act);

        UserLocalDataSource userLocalDataSource = UserLocalDataSource.getInstance(this);

        setUpToolbar();

        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame2);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame2, mealsFragment);
        transaction.commit();

        mUserRepository = UserRepository.getInstance(userLocalDataSource, UserRemoteDataSource.getInstance());


        //AuthStateListener causing problems ,may need get rid of it for admind functionality
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(MealsActivity.this, "You are signed in to fatties mobile app",
                            Toast.LENGTH_SHORT).show();

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

    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                    mUserRepository.saveData(user, null);
                }
            }
        });


        public static String getRefreshToken(){
            NotificationSharedPreference.init(this);
            NotificationSharedPreference noti =  NotificationSharedPreference.getInstance();
              return noti.getRefreshToken();

        }


    }


}
