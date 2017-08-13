package com.louanimashaun.fattyzgrill;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import com.louanimashaun.fattyzgrill.data.source.local.OrdersLocalDataSource;
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

    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.meals_act);

        setUpToolbar();

        MealsFragment mealsFragment = (MealsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (mealsFragment == null){
            mealsFragment = MealsFragment.newInstance();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, mealsFragment);
        transaction.commit();



        //AuthStateListener causing problems ,may need get rid of it for admind functionality
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(MealsActivity.this, "You are signed in to fatties mobile app",
                            Toast.LENGTH_SHORT).show();

                    final String userId = user.getUid();

                    //mUser Repository should be implemented through
                    // presenter to increase testability

                    mUserRepository.refreshData();
                    mUserRepository.getUser(userId, null, new DataSource.ErrorCallback() {
                        @Override
                        public void onError(int errorCode) {
                            if(errorCode == UserRemoteDataSource.UserNotFoundErrorCode){
                                User user = new User(userId, false);
                                mUserRepository.saveData(user, null);
                            }
                        }
                    });

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
}
