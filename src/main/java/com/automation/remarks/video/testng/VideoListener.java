package com.automation.remarks.video.testng;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.LinkedList;

import static com.automation.remarks.video.RecordingUtils.deleteRecordingOnSuccess;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoListener implements IInvokedMethodListener {

    private IVideoRecorder recorder;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Video video = getVideoAnnotation(method);
        if (video == null || !method.isTestMethod() || !video.enabled()) {
            return;
        }
        String fileName = getFileName(method, video);
        recorder = new VideoRecorder(fileName);
        recorder.start();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (recorder != null && method.isTestMethod()) {
            LinkedList<File> recordings = recorder.stop();
            if (testResult.isSuccess()) {
                deleteRecordingOnSuccess(recordings);
            }
        }
    }

    public String getFileName(IInvokedMethod method, Video video) {
        String name = video.name();
        if (name.length() == 0) {
            name = method.getTestMethod().getMethodName();
        }
        return name;
    }

    private Video getVideoAnnotation(IInvokedMethod method) {
        Annotation[] declaredAnnotations = method.getTestMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation.annotationType().equals(Video.class)) {
                return (Video) declaredAnnotation;
            }
        }
        return null;
    }
}
