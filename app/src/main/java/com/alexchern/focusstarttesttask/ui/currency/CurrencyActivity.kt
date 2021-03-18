package com.alexchern.focusstarttesttask.ui.currency

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexchern.focusstarttesttask.data.models.ApiResponse
import com.alexchern.focusstarttesttask.data.models.Currency
import com.alexchern.focusstarttesttask.databinding.ActivityMainBinding
import com.alexchern.focusstarttesttask.network.Resource
import com.alexchern.focusstarttesttask.utils.visible

class CurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var rvCurrency: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout

    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvCurrency = binding.rvCurrencies
        refreshLayout = binding.refreshLayout
        rvCurrency.layoutManager = LinearLayoutManager(this)

        refreshLayoutListener()

        if (savedInstanceState == null) refreshCurrencies()

        viewModel.currencies.observe(this) { apiResponse ->
            updateUi(apiResponse)
        }
    }

    private fun buttonConvert(currencies: Map<String, Currency>) {
        binding.buttonConvert.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val currency = binding.spinnerCurrency.selectedItem.toString()

            viewModel.convert(amount, currency, currencies)
        }
    }

    private fun refreshLayoutListener() {
        refreshLayout.setOnRefreshListener {
            if (refreshLayout.isRefreshing) {
                refreshCurrencies()

                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun updateUi(apiResponse: Resource<ApiResponse>) {
        when (apiResponse) {
            is Resource.Success -> {
                val currencies = apiResponse.data.currency

                loadCurrencies(currencies)

                binding.progressBar.visible(false)

                buttonConvert(currencies)

                viewModel.convertedCurrency.observe(this) { convertResult ->
                    binding.tvResult.text = convertResult.toString()
                }
            }

            is Resource.Failure -> {
                binding.progressBar.visible(false)

                loadCurrencies(null)

                if (apiResponse.isNetworkError) {
                    showMessage("Нет соединения с интернетом")
                } else {
                    showMessage("Ошибка")
                }
            }

            is Resource.Loading -> binding.progressBar.visible(true)
        }
    }

    private fun loadCurrencies(currencies: Map<String, Currency>?) {
        rvCurrency.adapter = CurrencyAdapter(currencies)
    }

    private fun refreshCurrencies() {
        viewModel.getCurrencies()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}