package com.example.nexguard.core.signaling

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

interface SignalingListener {

    fun onRoomCreated(roomId: String)

    fun onRoomJoined(roomId: String)

    fun onOffer(sessionDescription: SessionDescription)

    fun onAnswer(sessionDescription: SessionDescription)

    fun onIceCandidate(candidate: IceCandidate)

    fun onUserConnected()

    fun onUserDisconnected()

    fun onError(message: String)
}