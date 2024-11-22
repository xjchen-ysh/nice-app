package com.zyr.nice.feature.sheetdetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val SHEET_DETAIL_ROUTE = "sheet_detail"
const val SHEET_ID = "sheet_id"


/**
 * 跳转
 */
fun NavController.navigateToSheetDetail(id: Long): Unit {
    navigate("${SHEET_DETAIL_ROUTE}/$id")
}

/**
 * 配置导航
 */
fun NavGraphBuilder.sheetDetailScreen(
    toMain: () -> Unit,
) {
    composable("${SHEET_DETAIL_ROUTE}/{${SHEET_ID}}",
        arguments = listOf(
            navArgument(SHEET_ID) { type = NavType.LongType }
        )
    ) {
        SheetDetailRoute(toMain = toMain)
    }
}