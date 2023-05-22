package com.example.currencyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.CurrencyExchangeRate
import com.example.currencyapp.repository.TcmbExchangeRatesRepository
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val tcmbExchangeRatesRepository = TcmbExchangeRatesRepository()
    private val _exchangeRates = MutableLiveData<List<CurrencyExchangeRate>>()
    val exchangeRates: LiveData<List<CurrencyExchangeRate>> = _exchangeRates

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            _exchangeRates.value = tcmbExchangeRatesRepository.tcmbExchangeRates()
        }
    }
}
