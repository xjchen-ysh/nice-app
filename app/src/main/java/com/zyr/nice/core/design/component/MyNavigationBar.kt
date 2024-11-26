package com.zyr.nice.core.design.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zyr.nice.core.config.Config
import com.zyr.nice.core.design.theme.SpaceExtraMedium
import com.zyr.nice.core.design.theme.SpaceSmallHeight
import com.zyr.nice.core.extension.clickableNoRipple
import com.zyr.nice.core.model.SONG_EMPTY
import com.zyr.nice.core.model.Song
import com.zyr.nice.feature.main.TopLevelDestination

/**
 * 底部导航
 */
@Composable
fun MyNavigationBar(
    destination: List<TopLevelDestination>,
    currentDestination: String,
    onNavigateToDestination: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    play: () -> Unit = {},
    pause: () -> Unit = {},
    curSong: Song = SONG_EMPTY()
): Unit {

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 8000,
                easing = LinearEasing
            )
        ),
        label = "color"
    )

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = SpaceExtraMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                MyAsyncImgCircle(
                    Config.RESOURCE_ENDPOINT.format(curSong.icon), Modifier
                        .size(20.dp)
                        .graphicsLayer(
                            rotationZ = angle
                        )
                )
                Text(
                    "${curSong.title} - ${curSong.artist}",
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            // 播放区域
            IconButton({
                if (isPlaying) pause() else play()
            }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Delete else Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .navigationBarsPadding()
        ) {

            destination.forEachIndexed { index, destination ->

                val selected = destination.route == currentDestination
                val color =
                    if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

                Column(
                    Modifier
                        .weight(1f)
                        .padding(vertical = SpaceExtraMedium)
                        .clickableNoRipple {
                            onNavigateToDestination(index)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = if (selected) destination.selectedIcon else destination.unselectedIcon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )

                    SpaceSmallHeight()

                    Text(stringResource(destination.titleTextId), color = color)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyNavigationBarPreview() {
    MyNavigationBar(
        destination = TopLevelDestination.entries,
        currentDestination = TopLevelDestination.DISCOVERY.route,
        onNavigateToDestination = {},
    )
}