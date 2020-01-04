package com.example.revolut.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.revolut.model.Currency;
import com.example.revolut.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UtilsClassTest {

    @Mock
    Context context;

    @Mock
    private Resources mockContextResources;

    @Mock
    Drawable mDrawable;

    @Mock
    Utils utils;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        utils = new Utils();
    }

    @Test
    public void getCorrectListOfCurrencyFromJson(){
        String jsonText = "{\"AUD\":1.619,\"BGN\":1.9589,\"BRL\":4.7995}";

        JsonObject json = new Gson().fromJson(jsonText, JsonObject.class);

        List<Currency> list = new ArrayList<>();

        list.add(new Currency("AUD", 1.619));
        list.add(new Currency("BGN", 1.9589));
        list.add(new Currency("BRL", 4.7995));

        assertNotNull(utils.getCurrencies(json));
        assertTrue(utils.getCurrencies(json).equals(list));
    }

    @Test
    public void getCurrencyFlagMethodReturnsCorrectDrawableAndNotNull(){

        String image_txt = "ic_usd_flag";

        when(context.getResources()).thenReturn(mockContextResources);

        when(utils.getCurrencyFlag(context, image_txt)).thenReturn(mDrawable);

        int image_int = context.getResources().getIdentifier(image_txt, "drawable", context.getPackageName());

        Drawable drawable = context.getResources().getDrawable(image_int);

        assertNotNull(utils.getCurrencyFlag(context, image_txt));

        assertTrue(Objects.equals(utils.getCurrencyFlag(context, image_txt), drawable));
    }

    @Test
    public void getCurrencyNameMethodReturnsCorrectStringAndNotNull(){

        String curr_txt = "currency_usd" ;

        when(context.getResources()).thenReturn(mockContextResources);

        when(utils.getCurrencyName(context, curr_txt)).thenReturn("currency_usd");

        int curr_int = context.getResources().getIdentifier(curr_txt, "string", context.getPackageName());
        String curr_string = context.getResources().getString(curr_int);

        assertNotNull(utils.getCurrencyName(context, curr_txt));

        assertTrue(Objects.equals(utils.getCurrencyName(context, curr_txt), curr_string));

    }
}
