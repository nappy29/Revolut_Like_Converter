package com.example.revolut;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.revolut.model.Currency;
import com.example.revolut.ui.main.MainFragment;
import com.example.revolut.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

import javax.inject.Inject;


public class MainViewModelTest {

    private MainViewModel mainViewModel;

    @Inject
    Context context;

    @Inject
    CurrencyViewModelFactory currencyViewModelFactory;

    @Before
    public void setUpViewModel() {
        mainViewModel = ViewModelProviders.of((FragmentActivity) context, currencyViewModelFactory).get(MainViewModel.class);
    }

    @Test
    public void fetchAllPlacesSuccess() {
        List<Currency> currencies = mainViewModel.getCurrencies().getValue();
        assertNotNull(currencies);
        assertEquals(33,currencies.size());
        assertEquals("EUR", currencies.get(0).getCurency_code());

    }


}
