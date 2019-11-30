package com.example.revolut.di.module;

import android.app.Application;
import android.content.Context;

import com.example.revolut.data.CurrencyRepository;
import com.example.revolut.service.CurrencyApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    private static final String BASE_URL = "https://revolut.duckdns.org/";

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    CurrencyApiService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(CurrencyApiService.class);
    }

    @Singleton
    @Provides
    CurrencyRepository provideCurrencyRepository(CurrencyApiService currencyApiService) {
        return new CurrencyRepository(currencyApiService);
    }

}
