package com.zyr.nice.feature.main

import com.zyr.nice.R
import com.zyr.nice.feature.discovery.DISCOVERY_ROUTE
import com.zyr.nice.feature.feed.FEED_ROUTE
import com.zyr.nice.feature.me.ME_ROUTE
import com.zyr.nice.feature.shortvideo.SHORT_VIDEO_ROUTE

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val titleTextId: Int,
    val route: String,
) {
    // 发现页面
    DISCOVERY(
        selectedIcon = R.drawable.main_fill,
        unselectedIcon = R.drawable.main,
        titleTextId = R.string.tab_main,
        route = DISCOVERY_ROUTE
    ),

    // 视频
    SHORT_VIDEO(
        selectedIcon = R.drawable.video_fill,
        unselectedIcon = R.drawable.video,
        titleTextId = R.string.tab_video,
        route = SHORT_VIDEO_ROUTE
    ),

    // 我的
    ME(
        selectedIcon = R.drawable.me_fill,
        unselectedIcon = R.drawable.me,
        titleTextId = R.string.tab_me,
        route = ME_ROUTE
    ),

    // 动态
    FEED(
        selectedIcon = R.drawable.feed_fill,
        unselectedIcon = R.drawable.feed,
        titleTextId = R.string.tab_feed,
        route = FEED_ROUTE
    ),
}
