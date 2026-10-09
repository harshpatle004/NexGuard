package com.example.nexguard.core.signaling

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

interface SignalingListener {

    fun onRoomCreated(roomId: String)

    fun onRoomJoined(roomId: String)

    fun onUserConnected()

    fun onUserDisconnected()

    fun onOffer(offer: SessionDescription)

    fun onAnswer(answer: SessionDescription)

    fun onIceCandidate(candidate: IceCandidate)

    fun onError(message: String)
}