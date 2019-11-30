package com.example.revolut.di.builder;

import com.example.revolut.model.Currency;
import com.example.revolut.ui.main.MainFragment;
import com.example.revolut.ui.main.MainViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();
}
