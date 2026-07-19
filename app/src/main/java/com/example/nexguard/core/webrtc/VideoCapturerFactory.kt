package com.example.nexguard.core.webrtc

import android.content.Context
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraVideoCapturer
import org.webrtc.VideoCapturer

object VideoCapturerFactory {


    fun create(context: Context): VideoCapturer {

        val enumerator = Camera2Enumerator(context)

        val deviceNames =  enumerator.deviceNames

        deviceNames.forEach {

            if (enumerator.isFrontFacing(it)) {

                return enumerator.createCapturer(it, null)
                    ?: throw IllegalStateException("Cannot open camera")

            }

        }

        deviceNames.forEach {

            if (!enumerator.isFrontFacing(it)) {

                return enumerator.createCapturer(it, null)
                    ?: throw IllegalStateException("Cannot open camera")

            }

        }

        throw IllegalStateException("No camera found")

    }
}