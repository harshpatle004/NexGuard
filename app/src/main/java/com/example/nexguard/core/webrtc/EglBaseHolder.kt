package com.example.nexguard.core.webrtc

import org.webrtc.EglBase

object EglBaseHolder {
    val eglBase: EglBase by lazy {
        EglBase.create()
    }
}