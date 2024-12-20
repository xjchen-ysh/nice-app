package com.zyr.nice.core.model

import com.zyr.nice.ui.Constant
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Long,
    val title: String,
    val uri: String, // 歌曲链接
    val icon: String = "", // 网络封面
    val album: String, // 专辑
    val artist: String, // 艺术家
    val genre: String, // 流派
    val lyricStyle: Int = Constant.VALUE0,
    val lyric: String = "",
    val trackNumber: Int = 1, // 歌曲轨道号
    val totalTrackCount: Int = 1, // 专辑中歌曲数量
    val duration: Int = 0, // 歌曲时长
)

fun SONG_EMPTY() = Song(
    id = 0,
    title = "",
    uri = "",
    icon = "",
    album = "",
    artist = "",
    genre = "",
    lyricStyle = Constant.VALUE_NO,
    lyric = "",
    trackNumber = 1,
    totalTrackCount = 1,
    duration = 0
)
