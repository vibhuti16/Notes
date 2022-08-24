package dev.io.notes.ui.component

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.io.notes.ui.screens.Destinations
import dev.io.notes.ui.screens.home.HomeScreen

import dev.io.notes.ui.screens.note.note.NoteScreen
import dev.io.notes.ui.theme.PaperTheme
import dev.io.notes.ui.theme.isSystemInDarkThemeCustom


@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RootComponent() {
    val isDark = isSystemInDarkThemeCustom()
    val systemUiController = rememberSystemUiController()
    PaperTheme(isDark) {
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect { systemUiController.setSystemBarsColor(Color.Transparent, darkIcons) }
        Surface(color = MaterialTheme.colors.background) {
            val scrollState = rememberLazyListState()
            val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.HOME_ROUTE
                ) {
                    composable(Destinations.HOME_ROUTE) {
                        HomeScreen(isDark, scrollState) { navController.navigate(it) }
                    }
                    composable(Destinations.NOTE_ROUTE) {
                        NoteScreen({ navController.navigate(it) })
                        { navController.navigateUp() }
                    }
                }
        }
    }
}
