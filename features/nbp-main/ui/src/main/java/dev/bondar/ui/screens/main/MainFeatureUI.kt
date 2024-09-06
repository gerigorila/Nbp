package dev.bondar.ui.screens.main

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
import dev.bondar.nbp_main.main.NbpMainViewModel
import dev.bondar.nbp_main.main.State
import dev.bondar.uikit.NbpTheme

@Composable
fun NbpMainScreen(modifier: Modifier = Modifier, onCurrencyClicked: (String?, String?) -> Unit) {
    val viewModel: NbpMainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val currentState = state

    NbpMainContent(currentState, modifier, onCurrencyClicked)
}

@Composable
private fun NbpMainContent(
    currentState: State,
    modifier: Modifier = Modifier,
    onCurrencyClicked: (String?, String?) -> Unit,
) {
    Column(modifier) {
        when (currentState) {
            is State.None -> Unit
            is State.Error -> ErrorMessage(currentState)
            is State.Loading -> ProgressIndicator(currentState)
            is State.Success -> RateList(currentState, onCurrencyClicked = onCurrencyClicked)
        }
    }
}

@Composable
private fun ErrorMessage(
    state: State.Error,
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
            RateList(rates = rates, modifier = modifier)
        }
    }
}

@Composable
private fun ProgressIndicator(
    state: State.Loading,
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
            RateList(rates = rates, modifier = modifier)
        }
    }
}