package com.example.nexguard.core.signaling


import com.example.nexguard.core.network.ServerConfig
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import org.webrtc.IceCandidate
import org.webrtc.SessionDescription

class SignalingClient(
    private val listener: SignalingListener
) {

    private val socket: Socket = IO.socket(ServerConfig.BASE_URL)

    private var currentRoomId: String? = null

    fun connect() {

        if (!socket.connected()) {
            socket.connect()
        }

        socket.on(SocketEvents.ROOM_CREATED) { args ->

            if (args.isNotEmpty()) {
                val roomId = args[0].toString()

                currentRoomId = roomId

                listener.onRoomCreated(roomId)
            }
        }

        socket.on(SocketEvents.ROOM_JOINED) { args ->

            if (args.isNotEmpty()) {
                val roomId = args[0].toString()

                currentRoomId = roomId

                listener.onRoomJoined(roomId)
            }
        }

        socket.on(SocketEvents.USER_CONNECTED) {
            listener.onUserConnected()
        }

        socket.on(SocketEvents.USER_DISCONNECTED) {
            listener.onUserDisconnected()
        }

        socket.on(SocketEvents.OFFER) { args ->

            if (args.isNotEmpty()) {

                val json = args[0] as JSONObject

                listener.onOffer(
                    SessionDescription(
                        SessionDescription.Type.OFFER,
                        json.getString("sdp")
                    )
                )
            }
        }

        socket.on(SocketEvents.ANSWER) { args ->

            if (args.isNotEmpty()) {

                val json = args[0] as JSONObject

                listener.onAnswer(
                    SessionDescription(
                        SessionDescription.Type.ANSWER,
                        json.getString("sdp")
                    )
                )
            }
        }

        socket.on(SocketEvents.ICE_CANDIDATE) { args ->

            if (args.isNotEmpty()) {

                val json = args[0] as JSONObject

                listener.onIceCandidate(
                    IceCandidate(
                        json.getString("sdpMid"),
                        json.getInt("sdpMLineIndex"),
                        json.getString("candidate")
                    )
                )
            }
        }

        socket.on(SocketEvents.ERROR) { args ->

            if (args.isNotEmpty()) {
                listener.onError(args[0].toString())
            }
        }
    }

    fun createRoom(roomId: String) {

        currentRoomId = roomId

        socket.emit(
            SocketEvents.CREATE_ROOM,
            roomId
        )
    }

    fun joinRoom(roomId: String) {

        currentRoomId = roomId

        socket.emit(
            SocketEvents.JOIN_ROOM,
            roomId
        )
    }

    fun sendOffer(sdp: SessionDescription) {

        val roomId = currentRoomId ?: return

        val json = JSONObject()

        json.put("roomId", roomId)
        json.put("type", "offer")
        json.put("sdp", sdp.description)

        socket.emit(SocketEvents.OFFER, json)
    }

    fun sendAnswer(sdp: SessionDescription) {

        val roomId = currentRoomId ?: return

        val json = JSONObject()

        json.put("roomId", roomId)
        json.put("type", "answer")
        json.put("sdp", sdp.description)

        socket.emit(SocketEvents.ANSWER, json)
    }

    fun sendIceCandidate(candidate: IceCandidate) {

        val roomId = currentRoomId ?: return

        val json = JSONObject()

        json.put("roomId", roomId)
        json.put("sdpMid", candidate.sdpMid)
        json.put("sdpMLineIndex", candidate.sdpMLineIndex)
        json.put("candidate", candidate.sdp)

        socket.emit(SocketEvents.ICE_CANDIDATE, json)
    }

    fun disconnect() {

        currentRoomId = null

        socket.off()
        socket.disconnect()
    }
}