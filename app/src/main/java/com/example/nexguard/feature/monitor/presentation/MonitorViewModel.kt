package com.example.nexguard.feature.monitor.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import org.webrtc.SurfaceViewRenderer
import com.example.nexguard.core.signaling.SignalingClient
import com.example.nexguard.core.signaling.SignalingListener

class MonitorViewModel : ViewModel() {

    private var signalingClient: SignalingClient? = null

    fun initialize(context: Context) {
        if (signalingClient != null) return

        signalingClient = SignalingClient(
            object : SignalingListener {



                override fun onRoomCreated(roomId: String) {
                    // Not needed for the receiver yet.
                }

                override fun onRoomJoined(roomId: String) {
                    // Room joined successfully.
                }

                override fun onUserConnected() {
                    // Camera phone connected.
                }

                override fun onUserDisconnected() {
                    // Camera phone disconnected.
                }

                override fun onOffer(offer: org.webrtc.SessionDescription) {
                    // We'll handle the WebRTC offer next.
                }

                override fun onAnswer(answer: org.webrtc.SessionDescription) {
                    // We'll handle the WebRTC answer next.
                }

                override fun onIceCandidate(candidate: org.webrtc.IceCandidate) {
                    // We'll handle ICE candidates next.
                }

                override fun onError(message: String) {
                    // We'll add visible error handling later.
                }
            }

        )


        signalingClient?.connect()
    }
    fun joinRoom(roomId: String) {
        if (roomId.length != 4 || !roomId.all { it.isDigit() }) {
            return
        }

        signalingClient?.joinRoom(roomId)
    }
}