package com.zyr.nice.feature.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.system.exitProcess

@Composable
fun MainRoute(
    finishPage: () -> Unit
) {
    MainScreen(finishPage)
}

@Composable
fun MainScreen(
    finishPage: () -> Unit = {}
) {
    Text(text = "CCCCCCCCCCCCCCCCCCCCCCCCCCCC")

    Button(
//        onClick = finishPage,
        onClick = {
            exitProcess(0)
        }, Modifier.padding(top = 100.dp)
    ) {
        Text("关闭")
    }
}
