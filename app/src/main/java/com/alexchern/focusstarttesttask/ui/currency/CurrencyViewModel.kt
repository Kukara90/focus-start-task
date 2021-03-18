package com.alexchern.focusstarttesttask.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexchern.focusstarttesttask.data.models.ApiResponse
import com.alexchern.focusstarttesttask.data.models.Currency
import com.alexchern.focusstarttesttask.network.CurrencyApi
import com.alexchern.focusstarttesttask.network.datasource.RemoteDataSource
import com.alexchern.focusstarttesttask.network.Resource
import com.alexchern.focusstarttesttask.repository.CurrencyRepository
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private lateinit var service: CurrencyRepository

    private val _currencies = MutableLiveData<Resource<ApiResponse>>(Resource.Loading)
    val currencies: LiveData<Resource<ApiResponse>>
        get() = _currencies

    private val _convertedCurrency = MutableLiveData<Double>()
    val convertedCurrency: LiveData<Double>
        get() = _convertedCurrency

    fun getCurrencies() = viewModelScope.launch {
        service = CurrencyRepository(RemoteDataSource().getRetrofitClient(CurrencyApi::class.java))

        _currencies.value = service.getCurrencies()
    }

    fun convert(
        amount: String,
        toCurrency: String,
        currencies: Map<String, Currency>
    ) {
        val fromAmount = amount.toFloatOrNull() ?: return

        _convertedCurrency.value = fromAmount * currencies[toCurrency]?.value!!
    }
}