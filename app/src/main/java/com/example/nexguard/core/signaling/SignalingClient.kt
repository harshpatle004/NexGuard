package com.example.nexguard.core.signaling

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

class SignalingClient(
    serverUrl: String,
    private val listener: SignalingListener
) {

    private val socket: Socket = IO.socket(serverUrl)

    fun connect() {

        socket.connect()

        socket.on(SocketEvents.ROOM_CREATED) {

            listener.onRoomCreated(it[0].toString())
        }

        socket.on(SocketEvents.ROOM_JOINED) {

            listener.onRoomJoined(it[0].toString())
        }

        socket.on(SocketEvents.USER_CONNECTED) {

            listener.onUserConnected()
        }

        socket.on(SocketEvents.USER_DISCONNECTED) {

            listener.onUserDisconnected()
        }

        socket.on(SocketEvents.OFFER) {

            val json = it[0] as JSONObject

            listener.onOffer(
                SessionDescription(
                    SessionDescription.Type.OFFER,
                    json.getString("sdp")
                )
            )
        }

        socket.on(SocketEvents.ANSWER) {

            val json = it[0] as JSONObject

            listener.onAnswer(
                SessionDescription(
                    SessionDescription.Type.ANSWER,
                    json.getString("sdp")
                )
            )
        }

        socket.on(SocketEvents.ICE_CANDIDATE) {

            val json = it[0] as JSONObject

            listener.onIceCandidate(
                IceCandidate(
                    json.getString("sdpMid"),
                    json.getInt("sdpMLineIndex"),
                    json.getString("candidate")
                )
            )
        }

        socket.on(SocketEvents.ERROR) {

            listener.onError(it[0].toString())
        }
    }

    fun createRoom(roomId: String) {
        socket.emit(SocketEvents.CREATE_ROOM, roomId)
    }

    fun joinRoom(roomId: String) {
        socket.emit(SocketEvents.JOIN_ROOM, roomId)
    }

    fun sendOffer(sdp: SessionDescription) {

        val json = JSONObject()

        json.put("type", "offer")
        json.put("sdp", sdp.description)

        socket.emit(SocketEvents.OFFER, json)
    }

    fun sendAnswer(sdp: SessionDescription) {

        val json = JSONObject()

        json.put("type", "answer")
        json.put("sdp", sdp.description)

        socket.emit(SocketEvents.ANSWER, json)
    }

    fun sendIceCandidate(candidate: IceCandidate) {

        val json = JSONObject()

        json.put("sdpMid", candidate.sdpMid)
        json.put("sdpMLineIndex", candidate.sdpMLineIndex)
        json.put("candidate", candidate.sdp)

        socket.emit(SocketEvents.ICE_CANDIDATE, json)
    }

    fun disconnect() {
        socket.disconnect()
        socket.off()
    }
}