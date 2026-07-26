package com.example.nexguard.core.signaling

object SocketEvents {

    const val CREATE_ROOM = "create-room"
    const val JOIN_ROOM = "join-room"

    const val ROOM_CREATED = "room-created"
    const val ROOM_JOINED = "room-joined"

    const val OFFER = "offer"
    const val ANSWER = "answer"

    const val ICE_CANDIDATE = "ice-candidate"

    const val USER_CONNECTED = "user-connected"
    const val USER_DISCONNECTED = "user-disconnected"

    const val ERROR = "error"
}