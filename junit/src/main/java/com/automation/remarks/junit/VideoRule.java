package com.automation.remarks.junit;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.RecordingUtils;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.automation.remarks.video.RecordingUtils.videoEnabled;


/**
 * Created by sergey on 4/21/16.
 */
public class VideoRule extends TestWatcher {

    private IVideoRecorder recorder;

    @Override
    protected void starting(Description description) {
        if (videoDisabled(description)) {
            return;
        }
        recorder = RecorderFactory.getRecorder(VideoRecorder.conf().recorderType());
        recorder.start();
    }

    @Override
    protected void succeeded(Description description) {
        String fileName = getFileName(description);
        File video = stopRecording(fileName);
        doVideoProcessing(true, video);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        if (videoDisabled(description)) {
            return;
        }
        String fileName = getFileName(description);
        File file = stopRecording(fileName);
        doVideoProcessing(false, file);
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        failed(e, description);
    }

    private boolean videoDisabled(Description description){
        return !videoEnabled(description.getAnnotation(Video.class));
    }

    protected String getFileName(Description description) {
        String methodName = description.getMethodName();
        Video video = description.getAnnotation(Video.class);
        return RecordingUtils.getVideoFileName(video,methodName);
    }

    private File stopRecording(String filename) {
        if (recorder != null) {
            return recorder.stopAndSave(filename);
        }
        return null;
    }
}
