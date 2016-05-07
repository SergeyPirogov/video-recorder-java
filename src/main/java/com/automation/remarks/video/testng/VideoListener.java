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

import static com.automation.remarks.video.RecordingMode.ANNOTATED;
import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.automation.remarks.video.VideoConfiguration.MODE;

/**
 * Created by sergey on 4/13/16.
 */
public class VideoListener implements IInvokedMethodListener {

    private IVideoRecorder recorder;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        boolean testMethod = method.isTestMethod();
        if (!testMethod) {
            return;
        }
        Video video = getVideoAnnotation(method);
        String fileName = getFileName(method, video);
        if (MODE.equals(ANNOTATED) && (video == null || !video.enabled())) {
            return;
        }
        recorder = new VideoRecorder(fileName);
        recorder.start();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (recorder != null && method.isTestMethod()) {
            LinkedList<File> recordings = recorder.stop();
            doVideoProcessing(testResult.isSuccess(), recordings);
        }
    }

    public String getFileName(IInvokedMethod method, Video video) {
        String methodName = method.getTestMethod().getMethodName();
        if (video == null) {
            return methodName;
        }
        String name = video.name();
        return name.length() > 1 ? name : methodName;
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
