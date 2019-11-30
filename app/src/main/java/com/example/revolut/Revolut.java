package com.example.revolut;

import android.app.Activity;
import android.app.Application;

import com.example.revolut.di.component.AppComponent;
import com.example.revolut.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class Revolut extends DaggerApplication {

//    @Inject
//    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
//
//    @Override
//    public void onCreate(){
//        super.onCreate();
//        DaggerAppComponent.builder()
//                .application(this)
//                .build()
//                .inject(this);
//
//    }
//
//    @Override
//    public DispatchingAndroidInjector<Activity> activityInjector() {
//        return dispatchingAndroidInjector;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent component = DaggerAppComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }
}