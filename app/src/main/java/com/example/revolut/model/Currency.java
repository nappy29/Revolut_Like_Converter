package com.example.revolut.model;

import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import java.util.Objects;

public class Currency {

    private String curency_code;
    private String curency_name;
    private String curency_flag;
    private double exch_rate;

    public Currency(String curency_code, double exch_rate) {
        this.curency_code = curency_code;
        this.exch_rate = exch_rate;
    }

    public String getCurency_code() {
        return curency_code;
    }

    public void setCurency_code(String curency_code) {
        this.curency_code = curency_code;
    }

    public double getExch_rate() {
        return exch_rate;
    }

    public void setExch_rate(double exch_rate) {
        this.exch_rate = exch_rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return Double.compare(currency.getExch_rate(), getExch_rate()) == 0 &&
                Objects.equals(getCurency_code(), currency.getCurency_code()) &&
                Objects.equals(curency_name, currency.curency_name) &&
                Objects.equals(curency_flag, currency.curency_flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurency_code(), curency_name, curency_flag, getExch_rate());
    }
}
