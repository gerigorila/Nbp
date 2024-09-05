package dev.bondar.nbp_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.bondar.ui.routes.LocalNavController
import dev.bondar.ui.routes.NbpMainRoute
import dev.bondar.ui.NbpMainScreen
import dev.bondar.ui.routes.NbpCurrencyRateInfoRoute
import dev.bondar.ui.screens.currency_rate_info.CurrencyRateInfoScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = NbpMainRoute
        ) {
            composable(NbpMainRoute) { NbpMainScreen() }
            composable(NbpCurrencyRateInfoRoute) { CurrencyRateInfoScreen() }
        }
    }
}