package com.zyr.nice.feature.sheetdetail

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import com.zyr.nice.core.model.Song

@Composable
fun SheetDetailRoute(
    viewModel: SheetDetailViewModel = viewModel(),
    toMain: () -> Unit
) {

    val song by viewModel.topSong.collectAsState()

    viewModel.initPlayer(
        LocalContext.current,
        Uri.parse("https://bloc.biohub.club/resources/common/music.mp3")
    )

    SheetDetailScreen(
        toMain = toMain,
        song = song,
        play = viewModel::play,
        pause = viewModel::pause
    )
}

@OptIn(UnstableApi::class)
@Composable
fun SheetDetailScreen(
    toMain: () -> Unit = {},
    song: Song?,
    play: () -> Unit = {},
    pause: () -> Unit = {},
) {
    Box(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {


//        val player = SimpleExoPlayer.Builder(LocalContext.current).build()
//        val mediaItem =
//            MediaItem.fromUri(Uri.parse("https://bloc.biohub.club/resources/common/music.mp3"))
//        val mediaSource = ProgressiveMediaSource.Factory(
//            DefaultDataSourceFactory(
//                LocalContext.current,
//                "user-agent"
//            )
//        ).createMediaSource(mediaItem)
//        player.prepare(mediaSource)

        Column {

            Text(
                text = "SheetDetailScreen ${song?.title}",
                modifier = Modifier.clickable { toMain() })

            IconButton({
                // 播放
                play()
//                player.play()
            }) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }

            IconButton({
                // 播放
                pause()
//                player.pause()
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
    }
}