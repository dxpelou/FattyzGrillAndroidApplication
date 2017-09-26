package com.louanimashaun.fattyzgrill.di;

import android.app.Application;

import com.louanimashaun.fattyzgrill.FattyzGrill;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by louanimashaun on 25/09/2017.
 */

@Singleton
@Component(modules = {OrderRepositoryModule.class,
        MealsRepositoryModule.class,
        AppModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class})

public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(FattyzGrill app);

    @Override
    void inject(DaggerApplication instance);


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
