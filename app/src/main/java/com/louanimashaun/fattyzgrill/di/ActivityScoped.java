package com.louanimashaun.fattyzgrill.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by louanimashaun on 25/09/2017.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScoped {}
