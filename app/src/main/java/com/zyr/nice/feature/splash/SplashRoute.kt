package com.zyr.nice.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zyr.nice.R
import com.zyr.nice.core.design.theme.AppTheme
import com.zyr.nice.util.SuperDateUtil

@Composable
fun SplashRoute(
    toMain: () -> Unit, viewModel: SplashViewModel = viewModel()
) {

    val timeLeft by viewModel.timeLeft.collectAsStateWithLifecycle()
    val navigateToMain by viewModel.navigateToMain.collectAsState()

    SplashScreen(
        timeLeft,
        onSkipAdClick = viewModel::onSkipAdClick,
    )

    if (navigateToMain) {
        LaunchedEffect(key1 = true) {
            toMain()
        }
    }
}

@Composable
fun SplashScreen(
    timeLeft: Long = 0, onSkipAdClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onSkipAdClick()
                }
                .height(200.dp)
                .width(200.dp)
                .align(Alignment.Center))
        Text(
            stringResource(
                id = R.string.splash_text,
                SuperDateUtil.currentYear(),
                SuperDateUtil.currentYYMMDD()
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        )

        Text(
            "倒计时: $timeLeft",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SplashRoutePreview() {
    AppTheme {
        SplashScreen()
    }
}