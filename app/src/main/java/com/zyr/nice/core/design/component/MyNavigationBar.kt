package com.zyr.nice.core.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zyr.nice.core.design.theme.SpaceExtraMedium
import com.zyr.nice.core.design.theme.SpaceSmallHeight
import com.zyr.nice.core.extension.clickableNoRipple
import com.zyr.nice.feature.main.TopLevelDestination

/**
 * 底部导航
 */
@Composable
fun MyNavigationBar(
    destination: List<TopLevelDestination>,
    currentDestination: String,
    onNavigateToDestination: (Int) -> Unit,
    modifier: Modifier = Modifier
): Unit {
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