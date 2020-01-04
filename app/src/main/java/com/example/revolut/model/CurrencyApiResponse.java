package com.example.revolut.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrencyApiResponse {

    private CurrencyApiResponse(){

    }

    private CurrencyApiResponse(String base, String date, String json){
        base_currency = base;
        this.date = date;
        results = new Gson().fromJson(json, JsonObject.class);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyApiResponse)) return false;
        CurrencyApiResponse that = (CurrencyApiResponse) o;
        return Objects.equals(getBase_currency(), that.getBase_currency()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getResults(), that.getResults());
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getBase_currency(), getDate(), getResults());
//    }

    public static CurrencyApiResponse create(String base, String date, String json){

        return new CurrencyApiResponse(base, date, json);
    }
}
