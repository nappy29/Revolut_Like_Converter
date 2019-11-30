package com.example.revolut.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.revolut.CurrencyViewModelFactory;
import com.example.revolut.ui.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindsMainViewModel(MainViewModel currencyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(CurrencyViewModelFactory viewModelFactory);
}
