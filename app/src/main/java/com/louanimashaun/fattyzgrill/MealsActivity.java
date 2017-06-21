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
import com.louanimashaun.fattyzgrill.data.MealsRepository;
import com.louanimashaun.fattyzgrill.data.source.local.MealsLocalDataSoure;
import com.louanimashaun.fattyzgrill.data.source.remote.MealsRemoteDataSource;
import com.louanimashaun.fattyzgrill.presenter.MealsPresenter;
import com.louanimashaun.fattyzgrill.view.MealsFragment;

import java.util.Arrays;

public class MealsActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final int RC_SIGN_IN = 1;

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

        MealsPresenter mMealsPresenter = new MealsPresenter(
                MealsRepository.getInstance(new MealsLocalDataSoure(), MealsRemoteDataSource.getInstance()), mealsFragment);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(MealsActivity.this, "You are signed in to fatties mobile app", Toast.LENGTH_SHORT).show();
                }else{
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    //,new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
