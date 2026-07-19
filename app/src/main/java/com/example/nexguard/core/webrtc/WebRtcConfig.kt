package com.example.nexguard.core.webrtc

import org.webrtc.PeerConnection

object WebRtcConfig {

    val iceServers = listOf(

        PeerConnection.IceServer.builder("stun:stun.l.google.com:19302")
            .createIceServer(),

        PeerConnection.IceServer.builder("stun:stun1.l.google.com:19302")
            .createIceServer(),

        PeerConnection.IceServer.builder("stun:stun2.l.google.com:19302")
            .createIceServer(),

        PeerConnection.IceServer.builder("stun:stun3.l.google.com:19302")
            .createIceServer(),

        PeerConnection.IceServer.builder("stun:stun4.l.google.com:19302")
            .createIceServer()
    )
}