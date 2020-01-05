package com.example.revolut.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.revolut.CurrencyViewModelFactory;
import com.example.revolut.R;
import com.example.revolut.adapter.CurrencyAdapter;
import com.example.revolut.callback.CurrencyClickCallback;
import com.example.revolut.callback.CurrencyUpdatInterface;
import com.example.revolut.databinding.MainFragmentBinding;
import com.example.revolut.model.Currency;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment implements CurrencyUpdatInterface {

    private MainViewModel mViewModel;


    @Inject
    CurrencyViewModelFactory currencyViewModelFactory;

    private MainFragmentBinding binding;
    private CurrencyAdapter currencyAdapter;
    private LinearLayoutManager layoutManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
//        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        binding.setIsLoading(true);

        currencyAdapter = new CurrencyAdapter(getContext(), this, currencyClickCallback);
        layoutManager = new LinearLayoutManager(getContext());

        binding.rvCurr.setAdapter(currencyAdapter);
        binding.rvCurr.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.errBut.setOnClickListener(view -> {
            mViewModel.fetchCurrency("EUR", 1.00);
            binding.errBut.setVisibility(View.GONE);
            binding.progressView.setVisibility(View.VISIBLE);

        });
        observeViewModel();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(this, currencyViewModelFactory).get(MainViewModel.class);
        mViewModel.currencyUpdatInterface = this;
    }

    private void observeViewModel(){
        mViewModel.getCurrencies().observe(this, currencies -> {
            if(currencies != null) {
                currencyAdapter.upDateListofCurrency(currencies);
            }

        });

        mViewModel.getLoading().observe(this, isLoading ->{
            if(isLoading != null)
                if(isLoading)
                    binding.progressView.setVisibility(View.VISIBLE);
                else
                    binding.progressView.setVisibility(View.GONE);
        });

        mViewModel.getErrror().observe(this, isError -> {
            if(isError != null)
                if(isError) {
                    binding.errBut.setVisibility(View.VISIBLE);
                    binding.progressView.setVisibility(View.GONE);

                    int textViewId = com.google.android.material.R.id.snackbar_text;
                    Snackbar snackbar = Snackbar.make(getView(), "Please ensure your device has an active internet connection", Snackbar.LENGTH_LONG);
                    TextView textView = snackbar.getView().findViewById(textViewId);

                    textView.setTextColor(Color.parseColor("#FFC107"));
                    snackbar.show();
                }
                else
                    binding.errBut.setVisibility(View.GONE);
        });
    }

    @Override
    public void onAmountUpdate(Double amount) {
        currencyAdapter.upDateAmount(amount);
    }

    private final CurrencyClickCallback currencyClickCallback = new CurrencyClickCallback() {

        @Override
        public void onClick(Currency currency) {
            mViewModel.setBaseValues(currency);
        }
    };
}
