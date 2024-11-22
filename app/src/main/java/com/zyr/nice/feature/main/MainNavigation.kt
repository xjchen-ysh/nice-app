package com.zyr.nice.feature.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zyr.nice.feature.splash.SPLASH_ROUTE

const val MAIN_ROUTE = "main"

/**
 * 跳转
 */
fun NavController.navigateToMain(): Unit {
    navigate(MAIN_ROUTE) {
        launchSingleTop = true // 单例模式，只开一个主界面
        popUpTo(SPLASH_ROUTE) {// 关闭 splash 界面之前的界面
            inclusive = true // splash 也一起关掉，默认false
        }
    }
}

/**
 * 配置导航
 */
fun NavGraphBuilder.mainScreen(
    finishPage: () -> Unit,
    toSheetDetail: (Long) -> Unit,
) {
    composable(MAIN_ROUTE) {
        MainRoute(finishPage, toSheetDetail)
    }
}