package com.example.revolut.ui.main;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.revolut.data.CurrencyRepository;
import com.example.revolut.model.Currency;
import com.example.revolut.model.CurrencyApiResponse;
import com.example.revolut.service.CurrencyApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Mock
    CurrencyApiService currencyApiService;

    @Mock
    private Observer<List<Currency>> mockObserver;

    TestScheduler testScheduler;

    CurrencyRepository currencyRepository;
    MainViewModel mainViewModel;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        testScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);

        when(currencyApiService.getCurrencies()).thenReturn(Observable.just(CurrencyApiResponse.create("EUR", "", "{\"AUD\":1.619,\"BGN\":1.9589,\"BRL\":4.7995}")));
        currencyRepository = new CurrencyRepository(currencyApiService);
        mainViewModel = spy(new MainViewModel(currencyRepository));

        mainViewModel.getCurrencies().observeForever(mockObserver);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testForNonNullValues(){

        assertNotNull(mainViewModel.getCurrencies());

        assertTrue(mainViewModel.getCurrencies().hasObservers());

        assertNotNull(currencyApiService.getCurrencies());

        currencyApiService.getCurrencies().test()
                .assertSubscribed()
                .assertValues(CurrencyApiResponse.create("EUR", "", "{\"AUD\":1.619,\"BGN\":1.9589,\"BRL\":4.7995}"))
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    public void liveDataContainsCorrectValues() {

        testScheduler.advanceTimeTo(0, TimeUnit.SECONDS);

        assertNull(mainViewModel.getCurrencies().getValue());

        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(mainViewModel.getCurrencies().getValue().size(), 4);

        List<Currency> list = new ArrayList<>();

        list.add(new Currency("EUR", 1.0));
        list.add(new Currency("AUD", 1.619));
        list.add(new Currency("BGN", 1.9589));
        list.add(new Currency("BRL", 4.7995));

        verify(mockObserver, times(1)).onChanged(list);
    }


    @Test
    public void setBaseValueCallsFetchCurrencyMethod() {

        Currency currency = new Currency("SEK", 1.1);
        mainViewModel.setBaseValues(currency);

        verify(mainViewModel, times(1)).fetchCurrency(currency.getCurency_code(), currency.getExch_rate());
    }

    @Test
    public void onCurrencyItemCLickedMethodSetsCorrectValue() {
        mainViewModel.onCurrencyItemClicked(2);
        assertEquals(mainViewModel.getPosition(), 2);

        mainViewModel.onCurrencyItemClicked(0);
        assertEquals(mainViewModel.getPosition(), 0);
    }

    @Test
    public void constructSourceListMethodCorrectlyBuildsList(){

        List<Currency> list = new ArrayList<>();

        list.add(new Currency("EUR", 1.0));
        list.add(new Currency("AUD", 1.619));
        list.add(new Currency("BGN", 1.9589));
        list.add(new Currency("BRL", 4.7995));

        List<Currency> newlist = new ArrayList<>();

        list.add(new Currency("EUR", 1.0));
        list.add(new Currency("AUD", 1.619));
        list.add(new Currency("BGN", 1.9589));
        list.add(new Currency("BRL", 4.7995));

        List<Currency> listofCurrencies = mainViewModel.constructSourceList(list);


        mainViewModel.onCurrencyItemClicked(1);

        assertTrue(Objects.equals(listofCurrencies.get(0), new Currency("AUD", 1.619)));

        mainViewModel.onCurrencyItemClicked(1);

        assertTrue(Objects.equals(listofCurrencies.get(0), new Currency("EUR", 1)));

        mainViewModel.onCurrencyItemClicked(0);
    }

}