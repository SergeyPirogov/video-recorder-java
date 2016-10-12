package com.automation.remarks.testng.utils;

import com.automation.remarks.video.RecordingUtils;
import com.automation.remarks.video.annotations.Video;
import org.testng.ITestResult;

import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;

/**
 * Created by sergey on 25.06.16.
 */
public class ListenerUtils {

    public static String getFileName(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Video video = getVideoAnnotation(result);
        return RecordingUtils.getVideoFileName(video,methodName);
    }
}
