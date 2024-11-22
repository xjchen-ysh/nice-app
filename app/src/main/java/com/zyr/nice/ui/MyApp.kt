package com.zyr.nice.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zyr.nice.feature.main.mainScreen
import com.zyr.nice.feature.main.navigateToMain
import com.zyr.nice.feature.sheetdetail.navigateToSheetDetail
import com.zyr.nice.feature.sheetdetail.sheetDetailScreen
import com.zyr.nice.feature.splash.SPLASH_ROUTE
import com.zyr.nice.feature.splash.splashScreen

@Composable
fun MyApp(navController: NavHostController) {

    NavHost(navController = navController, startDestination = SPLASH_ROUTE) {
        splashScreen(
            toMain = navController::navigateToMain
        )
        mainScreen(
            finishPage = navController::popBackStack,
            toSheetDetail = navController::navigateToSheetDetail
        )
        sheetDetailScreen(
            toMain = navController::navigateToMain
        )
    }
}