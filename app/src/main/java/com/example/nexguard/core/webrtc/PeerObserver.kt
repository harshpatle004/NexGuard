package com.example.nexguard.core.webrtc

import org.webrtc.*

open class PeerObserver : PeerConnection.Observer {

    override fun onSignalingChange(state: PeerConnection.SignalingState) {}

    override fun onIceConnectionChange(state: PeerConnection.IceConnectionState) {}

    override fun onIceConnectionReceivingChange(receiving: Boolean) {}

    override fun onIceGatheringChange(state: PeerConnection.IceGatheringState) {}

    override fun onIceCandidate(candidate: IceCandidate) {}

    override fun onIceCandidatesRemoved(candidates: Array<out IceCandidate>) {}

    override fun onAddStream(stream: MediaStream) {}

    override fun onRemoveStream(stream: MediaStream) {}

    override fun onDataChannel(dataChannel: DataChannel) {}

    override fun onRenegotiationNeeded() {}

    override fun onAddTrack(
        receiver: RtpReceiver,
        mediaStreams: Array<out MediaStream>
    ) {}

    override fun onConnectionChange(
        newState: PeerConnection.PeerConnectionState
    ) {}

    override fun onSelectedCandidatePairChanged(
        event: CandidatePairChangeEvent
    ) {}

    override fun onTrack(transceiver: RtpTransceiver) {}
}