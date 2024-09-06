package dev.bondar.nbp_navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object NbpMainScreen : Screen

    @Serializable
    data class NbpCurrencyRateInfoScreen(
        val code: String,
        val table: String,
    ) : Screen
}