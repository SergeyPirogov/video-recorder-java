package com.automation.remarks.testng;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestResult;

import java.io.File;
import java.util.List;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.automation.remarks.video.RecordingUtils.videoEnabled;

/**
 * Created by sergey on 18.06.16.
 */
public class VideoListener extends TestNgListener {

    private IVideoRecorder recorder;

    @Override
    public void onTestStart(ITestResult result) {
        if (videoDisabled(result) || !shouldIntercept(result)) {
            return;
        }
        recorder = RecorderFactory.getRecorder(VideoRecorder.conf().getRecorderType());
        recorder.start();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String fileName = getFileName(result);
        File video = stopRecording(fileName);
        doVideoProcessing(true, video);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (videoDisabled(result) || !shouldIntercept(result)) {
            return;
        }
        String fileName = getFileName(result);
        File file = stopRecording(fileName);
        doVideoProcessing(false, file);
    }

    private boolean videoDisabled(ITestResult result){
        return !videoEnabled(getVideoAnnotation(result));
    }

    public boolean shouldIntercept(ITestResult result) {
        List<String> listeners = result.getTestContext().getCurrentXmlTest().getSuite().getListeners();
        return listeners.contains(this.getClass().getName()) || shouldIntercept(result.getTestClass().getRealClass(), this.getClass());
    }

    private File stopRecording(String filename) {
        if (recorder != null) {
            return recorder.stopAndSave(filename);
        }
        return null;
    }
}
