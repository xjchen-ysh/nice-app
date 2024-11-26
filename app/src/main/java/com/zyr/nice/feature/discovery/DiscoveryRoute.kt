package com.zyr.nice.feature.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zyr.nice.R
import com.zyr.nice.core.design.theme.AppTheme
import com.zyr.nice.core.design.theme.SpaceExtraMedium
import com.zyr.nice.core.design.theme.SpaceOuter
import com.zyr.nice.core.model.Song
import com.zyr.nice.core.ui.DiscoveryPreviewParameterProvider
import com.zyr.nice.feature.song.component.ItemSong

@Composable
fun DiscoveryRoute(
    viewModel: DiscoveryViewModel = viewModel(),
    toSheetDetail: (Long) -> Unit,
    addMediaItem: (Song) -> Unit,
) {
    val datum by viewModel.datum.collectAsState()
    DiscoveryScreen(
        songs = datum,
        toSheetDetail = toSheetDetail,
        addMediaItem = addMediaItem,
        loadMore = viewModel::loadMore
    )
}

@Composable
fun DiscoveryScreen(
    toggleDrawer: () -> Unit = {},
    toSheetDetail: (Long) -> Unit = {},
    addMediaItem: (Song) -> Unit = {},
    toSearch: () -> Unit = {},
    loadMore: () -> Unit = {},
    songs: List<Song> = listOf()
) {
    Scaffold(
        topBar = {
            DiscoveryTopBar(
                toggleDrawer, toSearch
            )
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
//                .padding(top = innerPadding.calculateTopPadding()) // 使用这种方式避免底部导航栏被遮挡，或者在Scaffold中设置contentWindowInsets exclude(WindowInsets.navigationBars)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(SpaceOuter),
                verticalArrangement = Arrangement.spacedBy(SpaceExtraMedium)
            ) {
                itemsIndexed(songs) { index, it ->
                    ItemSong(data = it, modifier = Modifier
                        .clickable {
                            addMediaItem(it)
//                            toSheetDetail(it.id)
                        })
                    if (index == songs.lastIndex) {
                        loadMore()
                    }
                }

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryTopBar(toggleDrawer: () -> Unit, toSearch: () -> Unit) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = toggleDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable {
                        toSearch()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = stringResource(id = R.string.discovery_search),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(horizontal = SpaceExtraMedium)
                        .size(28.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
fun DiscoveryScreenPreview(
    @PreviewParameter(DiscoveryPreviewParameterProvider::class)
    songs: List<Song>
) {
    AppTheme {
        DiscoveryScreen(songs = songs)
    }
}