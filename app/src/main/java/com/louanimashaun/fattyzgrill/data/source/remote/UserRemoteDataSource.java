package com.louanimashaun.fattyzgrill.data.source.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.model.User;

import java.util.List;

/**
 * Created by louanimashaun on 30/06/2017.
 */

public class UserRemoteDataSource implements DataSource<User> {

    private static UserRemoteDataSource INSTANCE = null;

    private final FirebaseDatabase mFirebaseDatabase ;
    private static DatabaseReference mUsersReference;

    private static final String USERS_PATH = "users";
    private static String THIS_USER_PATH = null;


    public static final int UserNotFoundErrorCode = 0;

    public static  UserRemoteDataSource getInstance(){
        if (INSTANCE == null){
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }

    private UserRemoteDataSource(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersReference = mFirebaseDatabase.getReference(USERS_PATH);
    }

    @Override
    public void loadData(LoadCallback<User> loadCallback) {
        //not in
    }

    @Override
    public void getData(String id, GetCallback getCallback) {
        String thisUserPath = USERS_PATH + "/" + id;
        DatabaseReference thisUserReference  = mFirebaseDatabase.getReference(thisUserPath);

        if(thisUserReference == null){
            getCallback.onDataNotAvailable();
        }
    }


    public void getUser(String id, final GetCallback<User> callback){
        DatabaseReference mThisUserReference = mUsersReference.child("/" + id);
        mThisUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null){
                    callback.onDataLoaded(user);
                }else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }


    public void getUser(String id, final GetCallback<User> callback,final ErrorCallback errorCallback){

        DatabaseReference mThisUserReference = mUsersReference.child("/" + id);

        Query query = mThisUserReference.equalTo(id, "userId");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    errorCallback.onError(UserNotFoundErrorCode);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });


        mThisUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null){
                    callback.onDataLoaded(user);
                }else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAll() {
        //not in use
    }

    @Override
    public void saveData(User data, final CompletionCallback callback) {
        mUsersReference.child(data.getUserId()).setValue(data, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                 callback.onComplete();
                    return;
                }
                callback.onCancel();
            }
        });
    }

    @Override
    public void saveData(List<User> data, CompletionCallback callback) {
        //noy in use
    }

}
