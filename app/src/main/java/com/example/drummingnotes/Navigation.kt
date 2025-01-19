package com.example.drummingnotes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drummingnotes.data.DrummingNotesViewModel
import com.example.drummingnotes.data.PDFViewerScreen
import com.example.drummingnotes.ui.theme.SongsDetailScreen
import com.example.drummingnotes.ui.theme.SongsScreen

object Routes {
    const val SCREEN_ALL_SONGS = "songsList"
    const val SCREEN_SONGS_DETAILS = "songDetails/{songId}"
    const val SCREEN_PDF_VIEWER = "pdfViewer"

    fun getSongDetailsPath(songId: Int?): String {
        return if (songId != null && songId != -1) {
            "songDetails/$songId"
        } else {
            "songDetails/0"
        }
    }
}


@Composable
fun NavigationController(
    viewModel: DrummingNotesViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SCREEN_ALL_SONGS) {
        composable(Routes.SCREEN_ALL_SONGS) {
            SongsScreen(
                viewModel = viewModel,
                navigation = navController
            )
        }
        composable(
            Routes.SCREEN_SONGS_DETAILS,
            arguments = listOf(
                navArgument("songId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("songId")?.let {
                SongsDetailScreen(
                    viewModel = viewModel,
                    navigation = navController,
                    songId = it
                )
            }
        }

        composable(
            route = "pdfViewerScreen/{pdfResId}",
            arguments = listOf(navArgument("pdfResId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pdfResId = backStackEntry.arguments?.getInt("pdfResId") ?: R.raw.blank_pdf
            PDFViewerScreen(pdfResId = pdfResId)
        }
    }
}

