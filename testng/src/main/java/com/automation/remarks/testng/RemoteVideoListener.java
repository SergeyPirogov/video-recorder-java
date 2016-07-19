package com.automation.remarks.testng;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.BaseRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;
import static com.automation.remarks.video.RecordingMode.ALL;

/**
 * Created by sergey on 12.05.16.
 */
public class RemoteVideoListener implements ITestListener {

    private static final String REMOTE = BaseRecorder.conf().getRemoteUrl();

    @Override
    public void onTestStart(ITestResult result) {
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String url = REMOTE + "/grid/admin/Video/start?&folder=" + BaseRecorder.conf().getVideoFolder();
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String url = REMOTE + "/grid/admin/Video/stop?result=true&name="+ testName;
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String url = REMOTE + "/grid/admin/Video/stop?result=false&name="+testName;
            sendRecordingRequest(url);
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

    private boolean videoEnabled(Video video) {
        return BaseRecorder.conf().isVideoEnabled()
                && (BaseRecorder.conf().getMode().equals(ALL)
                || (video != null && video.enabled()));
    }
}
