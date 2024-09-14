package dev.bondar.ui.routes

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController found!")
}

val LocalNavigationHandler = compositionLocalOf<((String?, String?) -> Unit)?> { null }
