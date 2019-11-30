package com.example.revolut.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.revolut.CurrencyViewModelFactory;
import com.example.revolut.R;
import com.example.revolut.adapter.CurrencyAdapter;
import com.example.revolut.callback.CurrencyClickCallback;
import com.example.revolut.callback.CurrencyUpdatInterface;
import com.example.revolut.databinding.MainFragmentBinding;
import com.example.revolut.model.Currency;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment implements CurrencyUpdatInterface {

    private MainViewModel mViewModel;

//    @BindView(R.id.rv_curr) RecyclerView recyclerView;

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
        });
    }

    @Override
    public void onAmountUpdate(Double amount) {
        currencyAdapter.upDateAmount(amount);
    }

    @Override
    public void onListUpdate(List<Currency> currencies){
//        currencyAdapter.upDateListofCurrency(currencies);
//        observeViewModel();
    }

    private final CurrencyClickCallback currencyClickCallback = new CurrencyClickCallback() {

        @Override
        public void onClick(Currency currency) {
            mViewModel.setBaseValues(currency);
        }
    };
}
