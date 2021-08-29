package com.hsjiang.lifecycle.compose.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hsjiang.lifecycle.compose.ui.theme.LifeCycleTheme

class BroadcastReceiverActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifeCycleTheme {
                collectPowerState()
            }
        }
    }
}

@Composable
private fun collectPowerState() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val connectState = remember { mutableStateOf(false) }

        systemBroadcastReceiver(Intent.ACTION_POWER_CONNECTED) { intent ->
            connectState.value = true
        }

        systemBroadcastReceiver(Intent.ACTION_POWER_DISCONNECTED) { intent ->
            connectState.value = false
        }

        Text(
            text = "power connected: ${connectState.value}",
            Modifier.wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
private fun systemBroadcastReceiver(action: String, onSystemEvent: (intent: Intent?) -> Unit) {
    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(newValue = onSystemEvent)

    // Safely use the latest onSystemEvent lambda passed to the functio
    DisposableEffect(key1 = context, key2 = action) {
        val intentFilter = IntentFilter(action)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }
        context.registerReceiver(broadcast, intentFilter)

        // When the effect leaves the Composition, remove the callback
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    LifeCycleTheme {
        collectPowerState()
    }
}