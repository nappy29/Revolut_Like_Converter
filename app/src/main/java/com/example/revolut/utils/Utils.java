package com.example.revolut.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.revolut.model.Currency;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

    public static List<Currency> getCurrencies(JsonObject jsonObject){
        List<Currency> currencies = new ArrayList<>();

        JSONObject result_object = null;
        Iterator<?> keys = null;
        try {
            result_object = new JSONObject(jsonObject.toString());
            keys = result_object.keys();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        while(keys.hasNext() ) {
            String key = (String) keys.next();

            try {
                Currency currency = new Currency(key, result_object.getDouble(key));
                currencies.add(currency);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
            return currencies;
    }

    public static Drawable getCurrencyFlag(Context context, String name){
        int image_txt = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(image_txt );

        return drawable;
    }

    public static String getCurrencyName(Context context, String name){
        int curr_txt = context.getResources().getIdentifier(name, "string", context.getPackageName());
        String curr_string = context.getResources().getString(curr_txt );

        return curr_string;
    }
}
