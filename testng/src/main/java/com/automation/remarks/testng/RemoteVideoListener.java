package com.automation.remarks.testng;

import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;

/**
 * Created by sergey on 12.05.16.
 */
public class RemoteVideoListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestName();
        Video video = getVideoAnnotation(result.getMethod());
        if (videoEnabled(video)) {
            String url = VideoConfiguration.REMOTE + "/grid/admin/Video/start";
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Video video = getVideoAnnotation(result.getMethod());
        if (videoEnabled(video)) {
            String url = VideoConfiguration.REMOTE + "/grid/admin/Video/stop";
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }

    private boolean videoEnabled(Video video){
        return video != null && video.enabled();
    }
}
