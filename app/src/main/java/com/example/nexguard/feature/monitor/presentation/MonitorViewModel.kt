package com.example.nexguard.feature.monitor.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import org.webrtc.SurfaceViewRenderer

class MonitorViewModel : ViewModel() {

    fun initialize(context: Context) {
        // TODO: Initialize PeerConnectionClient
    }

    fun joinRoom(roomId: String) {
        // TODO: Join signaling room
    }

    fun startRemoteVideo(renderer: SurfaceViewRenderer) {
        // TODO: Attach remote video track
    }

    fun release() {
        // TODO: Release WebRTC resources
    }

    override fun onCleared() {
        super.onCleared()
        release()
    }
}