package com.automation.remarks.testng;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.File;
import java.util.LinkedList;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.video.RecordingMode.ANNOTATED;
import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

/**
 * Created by sergey on 18.06.16.
 */
public class VideoListener implements ITestListener {

    private IVideoRecorder recorder;

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestNGMethod method = result.getMethod();
        Video video = getVideoAnnotation(method);
        if (VideoRecorder.conf().getMode().equals(ANNOTATED) && (video == null || !video.enabled())) {
            return;
        }
        String fileName = getFileName(method, video);
        recorder = new VideoRecorder(fileName);
        recorder.start();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LinkedList<File> recordings = stopRecording();
        doVideoProcessing(true, recordings);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LinkedList<File> recordings = stopRecording();
        doVideoProcessing(false, recordings);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        onTestFailure(result);
    }

    private LinkedList<File> stopRecording() {
        if (recorder != null) {
            return recorder.stop();
        }
        return new LinkedList<>();
    }
}
