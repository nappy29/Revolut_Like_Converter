package com.example.revolut.ui.main;

import com.example.revolut.data.CurrencyRepository;
import com.example.revolut.model.Currency;
import com.example.revolut.model.CurrencyApiResponse;
import com.example.revolut.service.CurrencyApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Observable;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    CurrencyApiService currencyApiService;

    CurrencyRepository currencyRepository;
    MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        currencyRepository = new CurrencyRepository(currencyApiService);
        mainViewModel = Mockito.spy(new MainViewModel(currencyRepository));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCurrencies() {
        when(mainViewModel.getCurrencies()).thenReturn(anyObject());
        assertNotNull(mainViewModel.getCurrencies().getValue());
    }

    @Test
    public void getLoading() {
    }

    @Test
    public void setBaseValues() {
    }
}