package com.louanimashaun.fattyzgrill.model;

/**
 * Created by louanimashaun on 30/06/2017.
 */

public class User {

    private boolean isAdmin;

    private String userId;


    public User(String userId, boolean isAdmin){
        this.userId = userId;
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
}
