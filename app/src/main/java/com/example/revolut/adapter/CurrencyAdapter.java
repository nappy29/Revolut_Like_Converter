package com.example.revolut.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revolut.R;
import com.example.revolut.callback.CurrencyClickCallback;
import com.example.revolut.databinding.CurrencyRowBinding;
import com.example.revolut.model.Currency;
import com.example.revolut.ui.main.MainViewModel;
import com.example.revolut.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    public  List<Currency> data = new ArrayList<>();

    private CurrencyClickCallback currencyClickCallback;
    double baseamount = 1.00;
    double base_currency_amount = 1.00;
    DecimalFormat twoDForm = new DecimalFormat("#.##");

    private Context context;

    public CurrencyAdapter(Context context, LifecycleOwner lifecycleOwner, CurrencyClickCallback currencyClickCallback){
        this.context = context;
        this.currencyClickCallback = currencyClickCallback;
    }
    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrencyRowBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.currency_row,
                        parent, false);

        CurrencyViewHolder currencyViewHolder = new CurrencyViewHolder(binding);

        return currencyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {

        holder.bind(data.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position, List<Object> payloads) {
        if(!payloads.isEmpty()) {
//            Log.e("Payload",  String.valueOf(payloads.get(0)));
            holder.bind(data.get(position));
        }
        else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {

        final CurrencyRowBinding binding;
        String currency_code = "";

        public CurrencyViewHolder(CurrencyRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("NewApi")
        public void bind(Currency currency){

            if(currency_code != currency.getCurency_code()){
                builRowView(currency);
                this.currency_code = currency.getCurency_code();
            }

            if(!binding.edRates.isFocused())
                binding.edRates.setText(Double.toString(Double.valueOf(twoDForm.format(currency.getExch_rate() * baseamount))));

            Log.d("new rate", String.valueOf(currency.getExch_rate() * baseamount) + " : " + currency.getExch_rate()
            + " : amount" + baseamount);

            String image_txt = "ic_" + currency.getCurency_code().toLowerCase() + "_flag";
            String curr_txt = "currency_" + currency.getCurency_code().toLowerCase();

            binding.currImage.setImageDrawable(Utils.getCurrencyFlag(context, image_txt));
            binding.currName.setText(Utils.getCurrencyName(context, curr_txt));


            binding.setCurrency(currency);
        }

        private void builRowView(Currency currency){
            binding.edRates.setOnFocusChangeListener((view, hasFocus) -> {
                if(hasFocus){
                    int position = getLayoutPosition();

                    if((position > 0 ? position : null ) != null) {
                        data.remove(position);
                        data.add(0, currency);
                        notifyItemMoved(position, 0);
                        Log.d("isdataMoved", "This data item has been moved");

                        MainViewModel.onCurrencyItemClicked(position);

                        base_currency_amount = currency.getExch_rate();
                    }
                }

            });

            binding.edRates.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if(binding.edRates.isFocused()) {
//                        currency.setExch_rate(Double.parseDouble(charSequence.toString()));
                        if(charSequence.toString().equals("") || charSequence.toString().equals("."))
                            charSequence = "0.00";

                        currency.setExch_rate(Double.parseDouble(charSequence.toString()));
                        currencyClickCallback.onClick(currency);

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    public void upDateAmount(Double baseamount){

        this.baseamount = baseamount/this.base_currency_amount;
        Log.e("amountreceived", String.valueOf(this.baseamount));
        notifyItemRangeChanged(0, data.size() - 1, this.baseamount);
    }

    public void upDateListofCurrency(List<Currency> currencies){


        if(data.isEmpty()){
            data = currencies;
            notifyItemRangeChanged(0, data.size() - 1, baseamount);
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return currencies.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return data.get(oldItemPosition).getCurency_code()
                            .equalsIgnoreCase(currencies.get(newItemPosition).getCurency_code());

//                    return true;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Currency new_cur = currencies.get(newItemPosition);
                    Currency old = data.get(oldItemPosition);
                    return new_cur.getCurency_code().equalsIgnoreCase(old.getCurency_code())
                            && new_cur.getExch_rate() == old.getExch_rate();
                }
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    return currencies.get(newItemPosition).getExch_rate();
//                    return super.getChangePayload(oldItemPosition, newItemPosition);
                }
            });

            result.dispatchUpdatesTo(this);

            data.clear();
            data.addAll(currencies);
        }

    }
}
