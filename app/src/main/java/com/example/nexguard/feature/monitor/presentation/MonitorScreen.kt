package com.example.nexguard.feature.monitor.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.webrtc.EglBase
import org.webrtc.SurfaceViewRenderer

@Composable
fun MonitorScreen() {

    val context = LocalContext.current

    val eglBase = EglBase.create()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),

            factory = {

                SurfaceViewRenderer(context).apply {

                    init(
                        eglBase.eglBaseContext,
                        null
                    )

                    setMirror(false)
                    setEnableHardwareScaler(true)
                }
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            eglBase.release()
        }
    }
}