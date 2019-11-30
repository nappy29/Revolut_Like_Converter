package com.example.revolut.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrencyApiResponse {

    @SerializedName("base")
    private String base_currency;


    @SerializedName("date")
    private String date;


    @SerializedName("rates")
    private JsonObject results;

    public JsonObject getResults() {
        return results;
    }
    public void setResults(JsonObject results) {
        this.results = results;
    }

    public String getBase_currency() {
        return base_currency;
    }

    public void setBase_currency(String base_currency) {
        this.base_currency = base_currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    class CurrencyDetailsApiResponse{
        JsonArray jsonElements;
    }
}
