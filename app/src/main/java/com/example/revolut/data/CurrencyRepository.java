package com.example.revolut.data;

import com.example.revolut.model.CurrencyApiResponse;
import com.example.revolut.service.CurrencyApiService;

import io.reactivex.Observable;

public class CurrencyRepository {
    private final CurrencyApiService currencyApiService;

    public CurrencyRepository(CurrencyApiService currencyApiService) {
        this.currencyApiService = currencyApiService;
    }

    public Observable<CurrencyApiResponse> getCurrencies() {
        return currencyApiService.getCurrencies();
    }

}
