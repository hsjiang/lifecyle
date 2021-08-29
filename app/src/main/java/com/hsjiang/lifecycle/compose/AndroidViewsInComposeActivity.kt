package com.hsjiang.lifecycle.compose

import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.hsjiang.lifecycle.compose.ui.theme.LifeCycleTheme

class AndroidViewsInComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifeCycleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    val context = LocalContext.current
//    val configuration = LocalConfiguration.current
//    val view = LocalView.current

    val checkedState = remember { mutableStateOf(false) }
    AndroidView(
        modifier = Modifier.wrapContentSize(Alignment.Center),
        factory = { context ->
            CheckBox(context).apply {
                text = "Android TextView init state"
                setOnCheckedChangeListener { view, checked ->
                    checkedState.value = checked
                }
            }
        },
        update = {
            it.text = "Android TextView checked: ${checkedState.value}"
        })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    LifeCycleTheme {
        Greeting2("Android")
    }
}