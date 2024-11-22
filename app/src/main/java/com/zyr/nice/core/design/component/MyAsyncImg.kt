package com.zyr.nice.core.design.component

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.zyr.nice.R
import com.zyr.nice.util.ResourceUtil

@Composable
fun MyAsyncImg(
    model: String?,
    modifier: Modifier = Modifier
): Unit {
    val modifier1 = modifier
        .clip(MaterialTheme.shapes.small)

    if (model.isNullOrBlank()) {
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier1
        )
    } else {
        AsyncImage(
            model = ResourceUtil.r(model),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier1
        )
    }
}