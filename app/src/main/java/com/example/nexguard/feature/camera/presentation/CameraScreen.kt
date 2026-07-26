package com.example.nexguard.feature.camera.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.nexguard.core.webrtc.PeerConnectionClient
import com.example.nexguard.core.webrtc.PeerConnectionListener
import org.webrtc.*

@Composable
fun CameraScreen() {

    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
        }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasPermission) return

    val peerClient = remember {

        PeerConnectionClient(
            context,
            object : PeerConnectionListener {

                override fun onIceCandidate(candidate: IceCandidate) {}

                override fun onConnectionStateChanged(
                    state: PeerConnection.PeerConnectionState
                ) {}

                override fun onRemoteVideoTrack(videoTrack: VideoTrack) {}

                override fun onLocalSessionDescription(
                    sessionDescription: SessionDescription
                ) {}
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            peerClient.release()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView(

            modifier = Modifier.fillMaxSize(),

            factory = {

                SurfaceViewRenderer(it).apply {

                    peerClient.startLocalPreview(this)
                }
            }
        )
    }
}