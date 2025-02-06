package com.dilip.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private var boundService: BoundService? = null
    private var isBound by mutableStateOf(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            boundService = (service as BoundService.LocalBinder).getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServiceAppUI()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }

    @Composable
    fun ServiceAppUI() {
        var randomNumber by remember { mutableIntStateOf(0) }

        TopAppBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Random Number: $randomNumber")

            Button(
                onClick = {
                    if (isBound) {
                        randomNumber = boundService?.getRandomNumber() ?: 0
                    }
                }
            ) {
                Text("Get Random Number")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Service Bound: ")
                Switch(
                    checked = isBound,
                    onCheckedChange = { checked ->
                        if (checked) {
                            bindService(Intent(this@MainActivity, BoundService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
                        } else {
                            unbindService(serviceConnection)
                            isBound = false
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            StatusText(isBound)
        }
    }

}

@Composable
fun StatusText(isBound: Boolean) {
    Text(
        text = if (isBound) "Bound Service Connected" else "Bound Service Not Connected",
        color = if (isBound) Color.Green else Color.Red,
        fontSize = 16.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun TopAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF6200EE)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bound Service",
            color = Color.White,
            fontSize = 20.sp
        )
    }
}