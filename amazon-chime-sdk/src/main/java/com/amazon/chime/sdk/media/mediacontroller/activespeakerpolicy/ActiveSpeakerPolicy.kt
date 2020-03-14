/*
 * Copyright (c) 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 */
package com.amazon.chime.sdk.media.mediacontroller.activespeakerpolicy

import com.amazon.chime.sdk.media.enums.VolumeLevel
import com.amazon.chime.sdk.media.mediacontroller.AttendeeInfo

/**
 * [ActiveSpeakerPolicy] calculates a normalized score of how active a speaker is. Implementations
 * of [ActiveSpeakerPolicy] provide custom algorithms for calculating the score.
 */
interface ActiveSpeakerPolicy {
    /**
     * Return the score of the speaker. If the score is 0, this speaker is not active.
     *
     * @param attendeeInfo: [AttendeeInfo] - Attendee information containing attendeeId and
     * externalUserId
     * @param volume: [VolumeLevel] - Current volume of attendee
     */
    fun calculateScore(attendeeInfo: AttendeeInfo, volume: VolumeLevel): Double
}
