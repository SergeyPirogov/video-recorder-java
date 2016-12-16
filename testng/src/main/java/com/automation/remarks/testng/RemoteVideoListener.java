package com.automation.remarks.testng;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.video.RecordingUtils.videoEnabled;

/**
 * Created by sergey on 12.05.16.
 */
public class RemoteVideoListener implements ITestListener {

    private static final String REMOTE = VideoRecorder.conf().getRemoteUrl();

    private RemoteVideoClient videoClient = new RemoteVideoClient(REMOTE);

    @Override
    public void onTestStart(ITestResult result) {
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            videoClient.videoStart();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            videoClient.videoStop(testName, true);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            videoClient.videoStop(testName, false);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
