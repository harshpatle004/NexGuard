package com.example.nexguard.feature.monitor.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.nexguard.core.webrtc.EglBaseHolder
import org.webrtc.SurfaceViewRenderer
import com.example.nexguard.feature.monitor.presentation.MonitorViewModel


@Composable
fun MonitorScreen(
    viewModel: MonitorViewModel
) {

    var roomId by remember { mutableStateOf("") }

    val context = LocalContext.current
    val eglBaseContext = EglBaseHolder.eglBase.eglBaseContext

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                SurfaceViewRenderer(context).apply {
                    init(eglBaseContext, null)
                    setMirror(false)
                    setEnableHardwareScaler(true)
                }
            },
            onRelease = { renderer ->
                renderer.release()
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            OutlinedTextField(
                value = roomId,
                onValueChange = { input ->
                    if (input.length <= 4 && input.all { it.isDigit() }) {
                        roomId = input
                    }
                },
                label = { Text("4-digit Camera ID") },
                singleLine = true
            )

            Button(
                onClick = {
                    viewModel.initialize(context)
                    viewModel.joinRoom(roomId)
                },
                enabled = roomId.length == 4
            ) {
                Text("Connect")
            }
        }
    }
}
