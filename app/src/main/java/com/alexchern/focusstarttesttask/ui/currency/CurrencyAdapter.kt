package com.alexchern.focusstarttesttask.ui.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexchern.focusstarttesttask.R
import com.alexchern.focusstarttesttask.data.models.Currency
import com.alexchern.focusstarttesttask.databinding.ValuteItemViewBinding

class CurrencyAdapter(
    private val currencies: Map<String, Currency>?,
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ValuteItemViewBinding.bind(view)

        val tvCurrencyName = binding.tvCurrencyName
        val tvValue = binding.tvValue
        val tvCurrencyFullName = binding.tvCurrencyFullName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.valute_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (currencies != null) {
            val currenciesList = currencies.values.toMutableList()

            holder.tvCurrencyName.text = currenciesList[position].charCode
            holder.tvValue.text = currenciesList[position].value.toString()
            holder.tvCurrencyFullName.text = currenciesList[position].name
        } else {
            holder.tvCurrencyFullName.text = "Невозможно загрузить список"
        }
    }

    override fun getItemCount(): Int = currencies?.size ?: 1
}