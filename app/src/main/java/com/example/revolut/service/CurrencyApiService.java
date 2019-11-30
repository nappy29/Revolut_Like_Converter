package com.example.revolut.service;

import com.example.revolut.model.CurrencyApiResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CurrencyApiService {

    @GET("latest?base=EUR")
    Observable<CurrencyApiResponse> getCurrencies();

    @GET("latest?base=EUR")
    Observable<CurrencyApiResponse> getCurrenciesdetails();
}
