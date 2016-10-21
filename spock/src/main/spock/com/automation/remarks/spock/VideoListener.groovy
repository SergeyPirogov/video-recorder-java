package com.automation.remarks.spock

import com.automation.remarks.video.RecorderFactory
import com.automation.remarks.video.RecordingUtils
import com.automation.remarks.video.annotations.Video
import com.automation.remarks.video.recorder.IVideoRecorder
import com.automation.remarks.video.recorder.VideoRecorder
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo

/**
 * Created by sepi on 21.10.16.
 */
class VideoListener extends AbstractRunListener {

    IVideoRecorder recorder
    def isSuccess = true

    @Override
    void beforeFeature(FeatureInfo feature) {
        isSuccess = true
        if (shouldIntercept(feature)) {
            recorder = RecorderFactory.getRecorder(VideoRecorder.conf().getRecorderType())
            recorder.start()
        }
    }

    @Override
    void error(ErrorInfo error) {
        isSuccess = false
    }

    @Override
    void afterFeature(FeatureInfo feature) {
        if (shouldIntercept(feature)) {
            def fileName = RecordingUtils.getVideoFileName(feature.getFeatureMethod().getAnnotation(Video), feature.name)
            def video = recorder.stopAndSave(fileName.replace(" ", "_"))
            RecordingUtils.doVideoProcessing(isSuccess, video)
        }
    }

    def static shouldIntercept(FeatureInfo feature) {
        Video video = feature.getFeatureMethod().getAnnotation(Video)
        return RecordingUtils.videoEnabled(video)
    }
}
