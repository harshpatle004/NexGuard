package com.example.nexguard.feature.camera.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexguard.core.network.ServerConfig
import com.example.nexguard.core.signaling.SignalingClient
import com.example.nexguard.core.signaling.SignalingListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel(), SignalingListener {

    private val _roomId = MutableStateFlow<String?>(null)
    val roomId: StateFlow<String?> = _roomId.asStateFlow()

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val signalingClient = SignalingClient(
        listener = this
    )

    fun createRoom() {
        if (_roomId.value != null) return

        _error.value = null

        val roomId = generateRoomId()

        signalingClient.connect()

        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            signalingClient.createRoom(roomId)
        }
    }

    private fun generateRoomId(): String {
        return (1000..9999).random().toString()
    }

    override fun onRoomCreated(roomId: String) {
        _roomId.value = roomId
    }

    override fun onRoomJoined(roomId: String) {
        // Not needed for camera room creation yet.
    }

    override fun onUserConnected() {
        // WebRTC will be connected here in the next step.
    }

    override fun onUserDisconnected() {
        // WebRTC cleanup will be handled later.
    }

    override fun onOffer(
        offer: org.webrtc.SessionDescription
    ) {
        // WebRTC will be handled later.
    }

    override fun onAnswer(
        answer: org.webrtc.SessionDescription
    ) {
        // WebRTC will be handled later.
    }

    override fun onIceCandidate(
        candidate: org.webrtc.IceCandidate
    ) {
        // WebRTC will be handled later.
    }

    override fun onError(message: String) {
        _error.value = message
    }

    override fun onCleared() {
        signalingClient.disconnect()
        super.onCleared()
    }
}