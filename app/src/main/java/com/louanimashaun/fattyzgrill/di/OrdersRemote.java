package com.louanimashaun.fattyzgrill.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by louanimashaun on 26/09/2017.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface OrdersRemote {

}
