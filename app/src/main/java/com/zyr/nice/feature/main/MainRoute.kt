package com.zyr.nice.feature.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zyr.nice.core.design.component.MyNavigationBar
import com.zyr.nice.core.design.theme.SpaceExtraSmallHeight
import com.zyr.nice.core.model.SONG_EMPTY
import com.zyr.nice.core.model.Song
import com.zyr.nice.feature.discovery.DiscoveryRoute
import com.zyr.nice.feature.feed.FeedRoute
import com.zyr.nice.feature.me.MeRoute
import com.zyr.nice.feature.shortvideo.ShortVideoRoute
import kotlinx.coroutines.launch

@Composable
fun MainRoute(
    finishPage: () -> Unit,
    toSheetDetail: (Long) -> Unit,
    viewModel: MainViewModel = viewModel()
) {

    val isPlaying by viewModel.isPlaying.observeAsState()
    val curSong by viewModel.curSong.collectAsState()

    viewModel.initPlayer(LocalContext.current)
    MainScreen(
        finishPage = finishPage,
        toSheetDetail = toSheetDetail,
        addMediaItem = viewModel::addMediaItem,
        play = viewModel::play,
        pause = viewModel::pause,
        isPlaying = isPlaying ?: false,
        curSong = curSong
    )
}

@Composable
fun MainScreen(
    finishPage: () -> Unit = {},
    toSheetDetail: (Long) -> Unit = {},
    addMediaItem: (Song) -> Unit = {},
    play: () -> Unit = {},
    pause: () -> Unit = {},
    isPlaying: Boolean = false,
    curSong: Song = SONG_EMPTY()
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
                    toSheetDetail = toSheetDetail,
                    addMediaItem = addMediaItem,
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
            },
            isPlaying = isPlaying,
            play = play,
            pause = pause,
            curSong = curSong
        )
    }


}


@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen({})
}