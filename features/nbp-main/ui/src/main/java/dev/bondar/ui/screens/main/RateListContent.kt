package dev.bondar.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bondar.nbp_main.main.RateUI
import dev.bondar.nbp_main.main.State
import dev.bondar.ui.routes.LocalNavController
import dev.bondar.ui.routes.NbpCurrencyRateInfoRoute
import dev.bondar.uikit.NbpTheme


@Composable
internal fun RateList(
    rateState: State.Success,
    modifier: Modifier = Modifier,
) {
    RateList(rates = rateState.rates, modifier)
}

@Composable
internal fun RateList(
    rates: List<RateUI>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(rates) { ratesUI ->
            key(ratesUI.code) {
                Rate(ratesUI)
            }
        }
    }
}

@Composable
internal fun Rate(
    rate: RateUI,
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    Row(
        modifier = modifier
            .padding(bottom = 4.dp)
            .clickable { navController.navigate(NbpCurrencyRateInfoRoute) },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(2f)
        ) {
            val title = rate.code
            if (title != null) {
                Text(
                    text = title,
                    style = NbpTheme.typography.headlineMedium,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            val description = rate.currency
            if (description != null) {
                Text(
                    text = description,
                    style = NbpTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            val mid = rate.mid
            if (mid != null) {
                Text(
                    text = mid.toString(),
                    style = NbpTheme.typography.bodyMedium,
                    maxLines = 3,
                    fontSize = 16.sp
                )
            }
        }
    }
}