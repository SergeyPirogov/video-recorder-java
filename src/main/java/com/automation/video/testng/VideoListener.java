package com.automation.video.testng;

import com.automation.video.annotations.Video;
import com.automation.video.recorder.VideoRecorder;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.List;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoListener implements IInvokedMethodListener {

    private VideoRecorder recorder;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        boolean testMethod = method.isTestMethod();
        Video video = getAnnotation(method);
        if (video == null || !testMethod || !VideoRecorder.videoEnabled()) {
            return;
        }
        String fileName = getFileName(method, video);
        recorder = new VideoRecorder(fileName);
        recorder.start();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (recorder != null) {
            List<File> recordings = recorder.stop();
            System.err.println(recordings);
        }
    }

    public String getFileName(IInvokedMethod method, Video video) {
        String name = video.name();
        if (name.length() == 0) {
            name = method.getTestMethod().getMethodName();
        }
        return name;
    }

    private Video getAnnotation(IInvokedMethod method) {
        return method.getTestMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Video.class);
    }
}
