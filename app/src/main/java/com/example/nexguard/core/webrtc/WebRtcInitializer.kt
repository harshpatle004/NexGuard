package com.example.nexguard.core.webrtc

import android.content.Context
import org.webrtc.PeerConnectionFactory

object WebRtcInitializer {

    private var initialized = false

    fun initialize(context: Context) {

        if (initialized) return

        PeerConnectionFactory.initialize(
            PeerConnectionFactory.InitializationOptions.builder(context)
                .createInitializationOptions()
        )

        initialized = true
    }
}