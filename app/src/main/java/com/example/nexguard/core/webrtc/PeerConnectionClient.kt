package com.example.nexguard.core.webrtc

import android.content.Context
import org.webrtc.*

class PeerConnectionClient(
    private val context: Context,
    private val listener: PeerConnectionListener
) {

    private val eglBase = EglBaseHolder.eglBase

    private lateinit var peerConnectionFactory: PeerConnectionFactory

    private var remoteRenderer: SurfaceViewRenderer? = null

    private lateinit var peerConnection: PeerConnection

    private lateinit var videoCapturer: VideoCapturer

    private lateinit var surfaceTextureHelper: SurfaceTextureHelper

    private lateinit var videoSource: VideoSource

    private lateinit var videoTrack: VideoTrack

    private var videoSender: RtpSender? = null

    init {
        WebRtcInitializer.initialize(context)

        initializePeerConnectionFactory()

        createPeerConnection()

        createLocalVideoTrack()
    }

    /**
     * Initialize PeerConnectionFactory
     */
    private fun initializePeerConnectionFactory() {

        val options = PeerConnectionFactory.Options()

        val encoderFactory = DefaultVideoEncoderFactory(
            eglBase.eglBaseContext,
            true,
            true
        )

        val decoderFactory = DefaultVideoDecoderFactory(
            eglBase.eglBaseContext
        )

        peerConnectionFactory =
            PeerConnectionFactory.builder()
                .setOptions(options)
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory)
                .createPeerConnectionFactory()
    }

    /**
     * Create PeerConnection
     */
    private fun createPeerConnection() {

        val rtcConfig = PeerConnection.RTCConfiguration(
            WebRtcConfig.iceServers
        )

        rtcConfig.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN

        peerConnection =
            peerConnectionFactory.createPeerConnection(
                rtcConfig,
                object : PeerObserver() {

                    override fun onIceCandidate(
                        candidate: IceCandidate
                    ) {
                        listener.onIceCandidate(candidate)
                    }

                    override fun onConnectionChange(
                        newState: PeerConnection.PeerConnectionState
                    ) {
                        listener.onConnectionStateChanged(newState)
                    }

                    override fun onTrack(
                        transceiver: RtpTransceiver
                    ) {

                        val track = transceiver.receiver.track()

                        if (track is VideoTrack) {

                            remoteRenderer?.let {
                                track.addSink(it)
                            }

                            listener.onRemoteVideoTrack(track)
                        }
                    }

                }
            ) ?: throw IllegalStateException(
                "Unable to create PeerConnection"
            )
    }

    /**
     * Create local camera video track
     */
  private  fun createLocalVideoTrack() {

        videoCapturer = VideoCapturerFactory.create(context)

        surfaceTextureHelper =
            SurfaceTextureHelper.create(
                "CaptureThread",
                eglBase.eglBaseContext
            )

        videoSource =
            peerConnectionFactory.createVideoSource(false)

        videoCapturer.initialize(
            surfaceTextureHelper,
            context,
            videoSource.capturerObserver
        )

        videoCapturer.startCapture(
            1280,
            720,
            30
        )

        videoTrack =
            peerConnectionFactory.createVideoTrack(
                "VIDEO_TRACK",
                videoSource
            )

        videoSender = peerConnection.addTrack(videoTrack)
    }

    /**
     * Attach local preview
     */
    fun startLocalPreview(
        renderer: SurfaceViewRenderer
    ) {

        renderer.init(
            eglBase.eglBaseContext,
            null
        )

        renderer.setMirror(true)

        renderer.setEnableHardwareScaler(true)

        videoTrack.addSink(renderer)
    }

    /**
     * Attach remote renderer
     */
    fun attachRemoteRenderer(renderer: SurfaceViewRenderer) {

        remoteRenderer = renderer

        renderer.init(
            eglBase.eglBaseContext,
            null
        )

        renderer.setMirror(false)

        renderer.setEnableHardwareScaler(true)
    }

    /**
     * Create SDP Offer
     */
    fun createOffer() {

        val constraints = MediaConstraints()

        peerConnection.createOffer(

            object : SdpObserverAdapter() {

                override fun onCreateSuccess(
                    sessionDescription: SessionDescription?
                ) {

                    if (sessionDescription == null) return

                    peerConnection.setLocalDescription(
                        object : SdpObserverAdapter() {

                            override fun onSetSuccess() {
                                listener.onLocalSessionDescription(sessionDescription)
                            }

                            override fun onSetFailure(error: String?) {
                                error?.let {
                                    throw IllegalStateException(it)
                                }
                            }

                        },
                        sessionDescription
                    )
                }

            },

            constraints
        )
    }

    /**
     * Create SDP Answer
     */
    fun createAnswer() {

        val constraints = MediaConstraints()

        peerConnection.createAnswer(

            object : SdpObserverAdapter() {

                override fun onCreateSuccess(
                    sessionDescription: SessionDescription?
                ) {

                    if (sessionDescription == null) return

                    peerConnection.setLocalDescription(
                        object : SdpObserverAdapter() {

                            override fun onSetSuccess() {
                                listener.onLocalSessionDescription(sessionDescription)
                            }

                            override fun onSetFailure(error: String?) {
                                error?.let {
                                    throw IllegalStateException(it)
                                }
                            }

                        },
                        sessionDescription
                    )
                }

            },

            constraints
        )
    }

    /**
     * Set remote SDP
     */
    fun setRemoteDescription(
        sessionDescription: SessionDescription
    ) {

        peerConnection.setRemoteDescription(
            object : SdpObserverAdapter() {

                override fun onSetSuccess() {
                    // Remote SDP applied successfully
                }

                override fun onSetFailure(error: String?) {
                    error?.let {
                        throw IllegalStateException(it)
                    }
                }

            },
            sessionDescription
        )
    }

    /**
     * Add ICE candidate received from signaling server
     */
    fun addIceCandidate(
        candidate: IceCandidate
    ) {

        peerConnection.addIceCandidate(candidate)
    }

    /**
     * Enable / Disable local video
     */
    fun setVideoEnabled(
        enabled: Boolean
    ) {

        videoTrack.setEnabled(enabled)
    }

    /**
     * Switch front/back camera
     */
    fun switchCamera() {

        val capturer = videoCapturer as? CameraVideoCapturer
            ?: return

        capturer.switchCamera(null)
    }

    /**
     * Stop capturing video
     */
    fun stopCapture() {

        try {

            videoCapturer.stopCapture()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Return local video track
     */
    fun getLocalVideoTrack(): VideoTrack {

        return videoTrack
    }

    /**
     * Return PeerConnection
     */
    fun getPeerConnection(): PeerConnection {

        return peerConnection
    }

    /**
     * Release all WebRTC resources.
     */
    fun release() {

        try {
            videoCapturer.stopCapture()
        } catch (_: Exception) {
        }

        try {
            videoCapturer.dispose()
        } catch (_: Exception) {
        }

        try {
            surfaceTextureHelper.dispose()
        } catch (_: Exception) {
        }

        try {
            videoSource.dispose()
        } catch (_: Exception) {
        }

        try {
            videoTrack.dispose()
        } catch (_: Exception) {
        }

        try {
            peerConnection.close()
            peerConnection.dispose()
        } catch (_: Exception) {
        }

        try {
            peerConnectionFactory.dispose()
        } catch (_: Exception) {
        }
    }



}

