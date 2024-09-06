package dev.bondar.ui.screens.currency_rate_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.bondar.nbp_main.currency_rate_info.CurrencyState
import dev.bondar.nbp_main.currency_rate_info.NbpCurrencyRateInfoViewModel
import dev.bondar.nbp_main.currency_rate_info.NbpCurrencyRateInfoViewModelFactory
import dev.bondar.uikit.NbpTheme


@Composable
fun NbpCurrencyRateInfoScreen(modifier: Modifier = Modifier, table: String, code:String) {
    val viewModel = hiltViewModel<NbpCurrencyRateInfoViewModel, NbpCurrencyRateInfoViewModelFactory> {
        factory -> factory.create(Pair(table, code))
    }
    val state by viewModel.state.collectAsState()
    val currentState = state

    CurrencyRateInfoContent(currentState, modifier)
}

@Composable
private fun CurrencyRateInfoContent(
    currentState: CurrencyState,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        when (currentState) {
            is CurrencyState.None -> Unit
            is CurrencyState.Error -> ErrorMessage(currentState)
            is CurrencyState.Loading -> ProgressIndicator(currentState)
            is CurrencyState.Success -> CurrencyRateList(currentState)
        }
    }
}

@Composable
private fun ErrorMessage(
    state: CurrencyState.Error,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier
                .fillMaxWidth()
                .background(NbpTheme.colorScheme.error)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error during update", color = NbpTheme.colorScheme.onError)
        }

        val rates = state.rates
        if (rates != null) {
            CurrencyRateList(rates = rates, modifier = modifier)
        }
    }
}

@Composable
private fun ProgressIndicator(
    state: CurrencyState.Loading,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        val rates = state.rates
        if (rates != null) {
            CurrencyRateList(rates = rates, modifier = modifier)
        }
    }
}