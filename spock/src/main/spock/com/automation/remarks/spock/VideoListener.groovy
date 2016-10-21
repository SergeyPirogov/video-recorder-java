package com.automation.remarks.spock

import com.automation.remarks.video.RecorderFactory
import com.automation.remarks.video.recorder.IVideoRecorder
import com.automation.remarks.video.recorder.VideoRecorder
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo

/**
 * Created by sepi on 21.10.16.
 */
class VideoListener extends AbstractRunListener {

    IVideoRecorder recorder

    @Override
    void beforeFeature(FeatureInfo feature) {
        recorder = RecorderFactory.getRecorder(VideoRecorder.conf().getRecorderType())
        recorder.start()
    }

    @Override
    void afterFeature(FeatureInfo feature) {
        recorder.stopAndSave(feature.name)
    }
}
