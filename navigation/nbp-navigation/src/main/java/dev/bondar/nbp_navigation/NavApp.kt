package dev.bondar.nbp_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.toRoute
import dev.bondar.ui.screens.currency_rate_info.NbpCurrencyRateInfoScreen
import dev.bondar.ui.screens.main.NbpMainScreen

@Composable
fun NavApp(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = rememberTypedNavGraph(navController)
    )
}

@Composable
private fun rememberTypedNavGraph(
    navController: NavController,
): NavGraph {
    return remember(navController) {
        navController.createGraph(
            startDestination = Screen.NbpMainScreen,
        ) {
            composable<Screen.NbpMainScreen> {
                NbpMainScreen(
                    onCurrencyClicked = { code: String?, table: String? ->
                        if (code == null || table == null) return@NbpMainScreen
                        navController.navigate(
                            Screen.NbpCurrencyRateInfoScreen(
                                code = code,
                                table = table,
                            )
                        )
                    }
                )
            }

            composable<Screen.NbpCurrencyRateInfoScreen> { backStackEntry ->
                val route = backStackEntry.toRoute<Screen.NbpCurrencyRateInfoScreen>()
                NbpCurrencyRateInfoScreen(code = route.code, table = route.table)
            }
        }
    }
}