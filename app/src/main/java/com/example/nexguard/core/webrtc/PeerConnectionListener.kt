package com.example.nexguard.core.webrtc

import org.webrtc.IceCandidate
import org.webrtc.PeerConnection
import org.webrtc.SessionDescription
import org.webrtc.VideoTrack

interface PeerConnectionListener {

    fun onIceCandidate(candidate: IceCandidate)

    fun onConnectionStateChanged(
        state: PeerConnection.PeerConnectionState
    )

    fun onRemoteVideoTrack(
        videoTrack: VideoTrack
    )

    fun onLocalSessionDescription(
        sessionDescription: SessionDescription
    )
}