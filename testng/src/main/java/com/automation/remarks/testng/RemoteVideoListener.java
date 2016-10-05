package com.automation.remarks.testng;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.ListenerUtils.videoEnabled;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;

/**
 * Created by sergey on 12.05.16.
 */
public class RemoteVideoListener implements ITestListener {

    private static final String REMOTE = VideoRecorder.conf().getRemoteUrl();

    @Override
    public void onTestStart(ITestResult result) {
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String folder_url = encodeFilePath(VideoRecorder.conf().getVideoFolder());
            String url = REMOTE + "/grid/admin/Video/start?&folder=" + folder_url ;
            sendRecordingRequest(url);
        }
    }

    private String encodeFilePath(File file){
        URL videoFolder = null;
        try {
            videoFolder = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return videoFolder.toString().replace("file:/","");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String url = REMOTE + "/grid/admin/Video/stop?result=true&name=" + testName;
            sendRecordingRequest(url);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getFileName(result);
        Video video = getVideoAnnotation(result);
        if (videoEnabled(video)) {
            String url = REMOTE + "/grid/admin/Video/stop?result=false&name=" + testName;
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
}
