package com.louanimashaun.fattyzgrill.model;

import io.realm.RealmObject;

/**
 * Created by louanimashaun on 30/06/2017.
 */

public class User extends RealmObject {

    private boolean isAdmin;

    private String userId;

    private String name;

    private String email;



    public User(){}

    public User(String userId, String name, String email,  boolean isAdmin){
        this.userId = userId;
        this.name = name;
        this.email = email;
       this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
