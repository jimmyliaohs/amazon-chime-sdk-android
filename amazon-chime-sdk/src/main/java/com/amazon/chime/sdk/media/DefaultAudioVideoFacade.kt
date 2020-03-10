/*
 * Copyright (c) 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 */

package com.amazon.chime.sdk.media

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.amazon.chime.sdk.media.devicecontroller.DeviceChangeObserver
import com.amazon.chime.sdk.media.devicecontroller.DeviceController
import com.amazon.chime.sdk.media.devicecontroller.MediaDevice
import com.amazon.chime.sdk.media.mediacontroller.AudioVideoControllerFacade
import com.amazon.chime.sdk.media.mediacontroller.AudioVideoObserver
import com.amazon.chime.sdk.media.mediacontroller.MetricsObserver
import com.amazon.chime.sdk.media.mediacontroller.RealtimeControllerFacade
import com.amazon.chime.sdk.media.mediacontroller.RealtimeObserver
import com.amazon.chime.sdk.media.mediacontroller.video.VideoRenderView
import com.amazon.chime.sdk.media.mediacontroller.video.VideoTileController
import com.amazon.chime.sdk.media.mediacontroller.video.VideoTileObserver

class DefaultAudioVideoFacade(
    private val context: Context,
    private val audioVideoController: AudioVideoControllerFacade,
    private val realtimeController: RealtimeControllerFacade,
    private val deviceController: DeviceController,
    private val videoTileController: VideoTileController
) : AudioVideoFacade {

    private val permissions = arrayOf(
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )

    override fun start() {
        val hasPermission: Boolean = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (hasPermission) {
            audioVideoController.start()
        } else {
            throw SecurityException(
                "Missing necessary permissions for WebRTC: ${permissions.joinToString(
                    separator = ", ",
                    prefix = "",
                    postfix = ""
                )}"
            )
        }
    }

    override fun addAudioVideoObserver(observer: AudioVideoObserver) {
        audioVideoController.addAudioVideoObserver(observer)
    }

    override fun removeAudioVideoObserver(observer: AudioVideoObserver) {
        audioVideoController.removeAudioVideoObserver(observer)
    }

    override fun addMetricsObserver(observer: MetricsObserver) {
        audioVideoController.addMetricsObserver(observer)
    }

    override fun removeMetricsObserver(observer: MetricsObserver) {
        audioVideoController.removeMetricsObserver(observer)
    }

    override fun stop() {
        audioVideoController.stop()
    }

    override fun startLocalVideo() {
        audioVideoController.startLocalVideo()
    }

    override fun stopLocalVideo() {
        audioVideoController.stopLocalVideo()
    }

    override fun startRemoteVideo() {
        audioVideoController.startRemoteVideo()
    }

    override fun stopRemoteVideo() {
        audioVideoController.stopRemoteVideo()
    }

    override fun realtimeLocalMute(): Boolean {
        return realtimeController.realtimeLocalMute()
    }

    override fun realtimeLocalUnmute(): Boolean {
        return realtimeController.realtimeLocalUnmute()
    }

    override fun addRealtimeObserver(observer: RealtimeObserver) {
        realtimeController.addRealtimeObserver(observer)
    }

    override fun removeRealtimeObserver(observer: RealtimeObserver) {
        realtimeController.removeRealtimeObserver(observer)
    }

    override fun listAudioDevices(): List<MediaDevice> {
        return deviceController.listAudioDevices()
    }

    override fun chooseAudioDevice(mediaDevice: MediaDevice) {
        deviceController.chooseAudioDevice(mediaDevice)
    }

    override fun getActiveCamera(): MediaDevice? {
        return deviceController.getActiveCamera()
    }

    override fun switchCamera() {
        deviceController.switchCamera()
    }

    override fun addDeviceChangeObserver(observer: DeviceChangeObserver) {
        deviceController.addDeviceChangeObserver(observer)
    }

    override fun removeDeviceChangeObserver(observer: DeviceChangeObserver) {
        deviceController.removeDeviceChangeObserver(observer)
    }

    override fun bindVideoView(videoView: VideoRenderView, tileId: Int) {
        videoTileController.bindVideoView(videoView, tileId)
    }

    override fun unbindVideoView(tileId: Int) {
        videoTileController.unbindVideoView(tileId)
    }

    override fun addVideoTileObserver(observer: VideoTileObserver) {
        videoTileController.addVideoTileObserver(observer)
    }

    override fun removeVideoTileObserver(observer: VideoTileObserver) {
        videoTileController.removeVideoTileObserver(observer)
    }

    override fun pauseRemoteVideoTile(tileId: Int) {
        videoTileController.pauseRemoteVideoTile(tileId)
    }

    override fun resumeRemoteVideoTile(tileId: Int) {
        videoTileController.resumeRemoteVideoTile(tileId)
    }
}
