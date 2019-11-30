package com.example.revolut.callback;

import com.example.revolut.model.Currency;

import java.util.List;

public interface CurrencyUpdatInterface {

    void onAmountUpdate(Double amount);
    void onListUpdate(List<Currency> currency);
}
