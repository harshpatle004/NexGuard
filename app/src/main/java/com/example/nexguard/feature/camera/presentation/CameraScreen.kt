package com.example.nexguard.feature.camera.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.nexguard.core.signaling.SignalingClient
import com.example.nexguard.core.signaling.SignalingListener
import com.example.nexguard.core.webrtc.PeerConnectionClient
import com.example.nexguard.core.webrtc.PeerConnectionListener
import org.webrtc.IceCandidate
import org.webrtc.PeerConnection
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoTrack

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

    var roomCreated by remember {
        mutableStateOf(false)
    }

    var roomId by remember {
        mutableStateOf<String?>(null)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        hasPermission = it
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasPermission) return

    val peerClient = remember {

        PeerConnectionClient(
            context,
            object : PeerConnectionListener {

                override fun onIceCandidate(
                    candidate: IceCandidate
                ) {
                }

                override fun onConnectionStateChanged(
                    state: PeerConnection.PeerConnectionState
                ) {
                }

                override fun onRemoteVideoTrack(
                    videoTrack: VideoTrack
                ) {
                }

                override fun onLocalSessionDescription(
                    sessionDescription: SessionDescription
                ) {
                }
            }
        )
    }

    val signalingClient = remember {

        SignalingClient(
            listener = object : SignalingListener {

                override fun onRoomCreated(
                    createdRoomId: String
                ) {
                    roomId = createdRoomId
                    roomCreated = true
                }

                override fun onRoomJoined(
                    joinedRoomId: String
                ) {
                }

                override fun onOffer(
                    sessionDescription: SessionDescription
                ) {
                }

                override fun onAnswer(
                    sessionDescription: SessionDescription
                ) {
                }

                override fun onIceCandidate(
                    candidate: IceCandidate
                ) {
                    android.util.Log.d(
                        "NEXGUARD",
                        "Local ICE candidate: ${candidate.sdp}"
                    )
                }

                override fun onUserConnected() {
                }

                override fun onUserDisconnected() {
                }

                override fun onError(
                    message: String
                ) {
                    errorMessage = message
                }
            }
        )
    }

    DisposableEffect(Unit) {

        onDispose {
            signalingClient.disconnect()
            peerClient.release()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),

            factory = { ctx ->

                SurfaceViewRenderer(ctx).apply {
                    peerClient.startLocalPreview(this)
                }
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            if (roomId == null) {

                Button(
                    onClick = {

                        errorMessage = null
                        roomCreated = false

                        val generatedRoomId =
                            (1000..9999).random().toString()

                        // Show the generated code immediately
                        roomId = generatedRoomId

                        // Connect
                        signalingClient.connect()

                        // Ask server to create it
                        signalingClient.createRoom(
                            generatedRoomId
                        )
                    }
                ) {
                    Text("Create Room")
                }

            } else {

                Text(
                    text = if (roomCreated)
                        "Room Created ✓"
                    else
                        "Creating Room...",
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = roomId!!,
                    fontSize = 40.sp
                )
            }

            errorMessage?.let {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = it
                )
            }
        }
    }
}