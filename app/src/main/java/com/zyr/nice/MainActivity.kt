package com.zyr.nice

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.zyr.nice.core.design.theme.AppTheme
import com.zyr.nice.ui.MyApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 显示到状态栏
        enableEdgeToEdge()

        // 更改状态栏文字颜色
        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )



        setContent {
            val navController = rememberNavController()
            AppTheme {
                MyApp(
                    navController = navController
                )
                /*Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) { innerPadding ->
                    Greeting(
                        name = stringResource(id = R.string.submit),
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.primary)
                    )

                }*/
            }
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}