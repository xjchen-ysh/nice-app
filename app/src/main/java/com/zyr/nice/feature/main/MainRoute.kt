package com.zyr.nice.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zyr.nice.core.design.component.MyNavigationBar
import com.zyr.nice.core.design.theme.SpaceExtraSmallHeight
import com.zyr.nice.feature.discovery.DiscoveryRoute
import com.zyr.nice.feature.feed.FeedRoute
import com.zyr.nice.feature.me.MeRoute
import com.zyr.nice.feature.shortvideo.ShortVideoRoute
import kotlinx.coroutines.launch

@Composable
fun MainRoute(
    finishPage: () -> Unit,
    toSheetDetail: (Long) -> Unit,
) {
    MainScreen(finishPage, toSheetDetail)
}

@Composable
fun MainScreen(
    finishPage: () -> Unit = {},
    toSheetDetail: (Long) -> Unit = {}
) {
    val pagerState = rememberPagerState { 4 }
    var currentDestination by rememberSaveable {
        mutableStateOf(TopLevelDestination.DISCOVERY.route)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // 主窗体页面
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = false,
            beyondViewportPageCount = 4, // 加载屏幕外的额外页面
        ) { page ->
            when (page) {
                0 -> DiscoveryRoute(
                    toSheetDetail = toSheetDetail
                )
                1 -> ShortVideoRoute()
                2 -> MeRoute()
                3 -> FeedRoute()
            }
        }

        SpaceExtraSmallHeight()

        // 底部导航
        MyNavigationBar(
            destination = TopLevelDestination.entries,
            currentDestination = currentDestination,
            onNavigateToDestination = { index ->
                currentDestination = TopLevelDestination.entries[index].route
                scope.launch {
                    pagerState.scrollToPage(index)
                }
            }
        )
    }


}


@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen({})
}