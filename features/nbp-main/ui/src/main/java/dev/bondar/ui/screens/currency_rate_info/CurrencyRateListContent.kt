package dev.bondar.ui.screens.currency_rate_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bondar.nbp_main.currency_rate_info.CurrencyRateUI
import dev.bondar.nbp_main.currency_rate_info.CurrencyState
import dev.bondar.uikit.NbpTheme


@Composable
internal fun CurrencyRateList(
    rateState: CurrencyState.Success,
    modifier: Modifier = Modifier,
) {
    CurrencyRateList(rates = rateState.rates, modifier)
}

@Composable
internal fun CurrencyRateList(
    rates: List<CurrencyRateUI>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rates.first().code.toString(),
                style = NbpTheme.typography.headlineMedium
            )
            Text(
                text = rates.first().currency.toString(),
                style = NbpTheme.typography.headlineMedium
            )
        }

        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(rates) { ratesUI ->
                key(ratesUI.code) {
                    CurrencyRate(ratesUI)
                }
            }
        }
    }
}

@Composable
internal fun CurrencyRate(
    rate: CurrencyRateUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            val date = rate.effectiveDate
            if (date != null) {
                Text(
                    text = date,
                    style = NbpTheme.typography.bodyMedium,
                    maxLines = 1,
                    fontSize = 24.sp
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
                    color = Color(rate.color),
                    style = NbpTheme.typography.bodyMedium,
                    maxLines = 3,
                    fontSize = 24.sp,
                )
            }
        }
    }
}