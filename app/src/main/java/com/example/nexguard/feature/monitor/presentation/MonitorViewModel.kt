
package com.example.nexguard.feature.monitor.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nexguard.core.signaling.SignalingClient
import com.example.nexguard.core.signaling.SignalingListener
import com.example.nexguard.core.webrtc.PeerConnectionClient
import com.example.nexguard.core.webrtc.PeerConnectionListener
import org.webrtc.IceCandidate
import org.webrtc.PeerConnection
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoTrack

class MonitorViewModel : ViewModel() {

    private var signalingClient: SignalingClient? = null
    private var peerClient: PeerConnectionClient? = null
    private var currentRoomId: String? = null

    fun initialize(context: Context) {
        if (signalingClient != null) return

        peerClient = PeerConnectionClient(
            context = context.applicationContext,
            listener = object : PeerConnectionListener {

                override fun onIceCandidate(candidate: IceCandidate) {
                    Log.d("NEXGUARD", "Receiver ICE candidate: ${candidate.sdp}")
                    signalingClient?.sendIceCandidate(candidate)
                }

                override fun onConnectionStateChanged(
                    state: PeerConnection.PeerConnectionState
                ) {
                    Log.d("NEXGUARD", "WebRTC state: $state")
                }

                override fun onRemoteVideoTrack(videoTrack: VideoTrack) {
                    Log.d("NEXGUARD", "Remote video track received")
                }

                override fun onLocalSessionDescription(
                    sessionDescription: SessionDescription
                ) {
                    if (sessionDescription.type == SessionDescription.Type.ANSWER) {
                        signalingClient?.sendAnswer(sessionDescription)
                        Log.d("NEXGUARD", "Answer sent to camera")
                    }
                }
            },
            captureLocalVideo = false
        )

        signalingClient = SignalingClient(
            object : SignalingListener {

                override fun onRoomCreated(roomId: String) {
                    Log.d("NEXGUARD", "Room created: $roomId")
                }

                override fun onRoomJoined(roomId: String) {
                    currentRoomId = roomId
                    Log.d("NEXGUARD", "ROOM JOINED: $roomId")
                }

                override fun onUserConnected() {
                    Log.d("NEXGUARD", "Camera user connected")
                }

                override fun onUserDisconnected() {
                    Log.d("NEXGUARD", "Camera user disconnected")
                }

                override fun onOffer(offer: SessionDescription) {
                    Log.d("NEXGUARD", "Offer received")

                    peerClient?.setRemoteDescription(offer) {
                        peerClient?.createAnswer()
                    }
                }

                override fun onAnswer(answer: SessionDescription) {
                    Log.d("NEXGUARD", "Answer received: ${answer.type}")
                }

                override fun onIceCandidate(candidate: IceCandidate) {
                    Log.d("NEXGUARD", "Remote ICE candidate received")
                    peerClient?.addIceCandidate(candidate)
                }

                override fun onError(message: String) {
                    Log.e("NEXGUARD", "Signaling error: $message")
                }
            }
        )

        signalingClient?.connect()
    }

    fun joinRoom(roomId: String) {
        Log.d("NEXGUARD", "Joining room: $roomId")

        if (roomId.length != 4 || !roomId.all { it.isDigit() }) {
            Log.e("NEXGUARD", "Invalid room ID")
            return
        }

        currentRoomId = roomId
        signalingClient?.joinRoom(roomId)
    }

    fun attachRemoteRenderer(renderer: SurfaceViewRenderer) {
        peerClient?.attachRemoteRenderer(renderer)
    }

    override fun onCleared() {
        signalingClient?.disconnect()
        peerClient?.release()

        signalingClient = null
        peerClient = null

        super.onCleared()
    }
}
