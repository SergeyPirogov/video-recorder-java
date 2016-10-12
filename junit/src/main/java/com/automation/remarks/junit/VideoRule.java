package com.automation.remarks.junit;

import com.automation.remarks.video.annotations.Video;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


/**
 * Created by sergey on 4/21/16.
 */
public class VideoRule extends TestWatcher {

    @Override
    protected void finished(Description description) {
    }

    @Override
    protected void starting(Description description) {
    }

    @Override
    protected void succeeded(Description description) {
    }

    @Override
    protected void failed(Throwable e, Description description) {
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
    }

    private String getFileName(Video video, Description description) {
        String methodName = description.getMethodName();
        if (video == null) {
            return methodName;
        }
        String videoName = video.name();
        return videoName.length() > 1 ? videoName : methodName;
    }
}
