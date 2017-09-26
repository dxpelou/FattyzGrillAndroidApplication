package com.louanimashaun.fattyzgrill.notifications;

/**
 * Created by louanimashaun on 26/09/2017.
 */

public interface TokenDataSource {

    String getRefreshToken();

    void saveRefreshToken(String token);
}
