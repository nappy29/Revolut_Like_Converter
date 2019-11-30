package com.example.revolut.adapter;

import android.annotation.SuppressLint;
import android.icu.text.DecimalFormat;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class CustomBindingAdapter {

    @SuppressLint("SetTextI18n")
    @BindingAdapter("doublevalue")
    public static void setText(EditText view, double value) {
        @SuppressLint("NewApi") DecimalFormat twoDForm = new DecimalFormat("#.##");
        view.setText(Double.toString(Double.valueOf(twoDForm.format(value))));
    }
}
