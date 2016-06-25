package com.automation.remarks.testng.utils;

import com.automation.remarks.video.annotations.Video;
import org.testng.ITestNGMethod;

/**
 * Created by sergey on 25.06.16.
 */
public class ListenerUtils {

    public static String getFileName(ITestNGMethod method, Video video) {
        String methodName = method.getMethodName();
        if (video == null) {
            return methodName;
        }
        String name = video.name();
        return name.length() > 1 ? name : methodName;
    }
}
