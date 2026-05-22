package com.example.tadam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tadam.data.model.RatesResponse
import com.example.tadam.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RatesUiState(
    val isLoading: Boolean = false,
    val base: String = "EUR",
    val rates: Map<String, Double> = emptyMap(),
    val lastUpdated: String = "",
    val selectedFromCurrency: String = "EUR",
    val selectedToCurrency: String = "USD",
    val amountText: String = "1.0",
    val convertedAmount: Double? = null,
    val error: String? = null
) {
    val availableCurrencies: List<String>
        get() = (listOf(base) + rates.keys).distinct().sorted()
}

class CurrencyViewModel(private val repository: CurrencyRepository = CurrencyRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(RatesUiState(isLoading = true))
    val uiState: StateFlow<RatesUiState> = _uiState

    init {
        fetchRates("EUR")
    }

    fun fetchRates(base: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null,
            base = base,
            selectedFromCurrency = base,
            convertedAmount = null,
        )
        viewModelScope.launch {
            try {
                val resp: RatesResponse = repository.getLatestRates(base)
                val currencies = listOf(resp.base) + resp.rates.keys
                val selectedTo = when {
                    _uiState.value.selectedToCurrency == resp.base -> currencies.firstOrNull { it != resp.base } ?: resp.base
                    currencies.contains(_uiState.value.selectedToCurrency) -> _uiState.value.selectedToCurrency
                    else -> currencies.firstOrNull { it != resp.base } ?: resp.base
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    rates = resp.rates,
                    base = resp.base,
                    lastUpdated = resp.date,
                    selectedFromCurrency = resp.base,
                    selectedToCurrency = selectedTo,
                    convertedAmount = null,
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Unknown error")
            }
        }
    }

    fun selectFromCurrency(currency: String) {
        if (currency == _uiState.value.selectedFromCurrency && _uiState.value.rates.isNotEmpty()) return
        _uiState.value = _uiState.value.copy(selectedFromCurrency = currency, error = null, convertedAmount = null)
        fetchRates(currency)
    }

    fun selectToCurrency(currency: String) {
        _uiState.value = _uiState.value.copy(selectedToCurrency = currency, convertedAmount = null)
    }

    fun updateAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amountText = amount, convertedAmount = null)
    }

    fun swapCurrencies() {
        val from = _uiState.value.selectedToCurrency
        val to = _uiState.value.selectedFromCurrency
        _uiState.value = _uiState.value.copy(selectedFromCurrency = from, selectedToCurrency = to, convertedAmount = null)
        fetchRates(from)
    }

    fun refresh() {
        fetchRates(_uiState.value.selectedFromCurrency)
    }

    fun convertNow() {
        _uiState.value = _uiState.value.copy(convertedAmount = convertAmount(_uiState.value.base, _uiState.value.rates, _uiState.value.amountText, _uiState.value.selectedToCurrency))
    }

    private fun convertAmount(base: String, rates: Map<String, Double>, amountText: String, target: String): Double? {
        val amount = amountText.toDoubleOrNull() ?: return null
        if (target == base) return amount
        val rate = rates[target] ?: return null
        return amount * rate
    }
}
