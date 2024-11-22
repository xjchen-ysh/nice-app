package com.zyr.nice.feature.song.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zyr.nice.core.design.component.MyAsyncImg
import com.zyr.nice.core.design.theme.SpaceMedium
import com.zyr.nice.core.design.theme.SpaceSmallHeight
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.ui.DiscoveryDataPreviewParameterProvider.SONG


@Composable
fun ItemSong(data: Song, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        MyAsyncImg(
            data.icon, Modifier
                .size(50.dp)
        )


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = SpaceMedium)
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            SpaceSmallHeight()

            Text(
                text = "${data.artist} - ${data.album}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ItemSongPreview() {
    ItemSong(SONG)
}