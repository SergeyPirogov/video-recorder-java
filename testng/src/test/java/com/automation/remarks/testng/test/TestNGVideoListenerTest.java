package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGVideoListenerTest extends BaseTest {

    @Test
    @Video
    public void shouldBeOneRecordingOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess()  {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists());
    }

    @Test
    @Video(enabled = false)
    public void shouldNotBeRecordingIfVideoDisabled() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists());
    }

    @Test
    @Video(name = "new_recording")
    public void shouldBeRecordingWithCustomName() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
        assertTrue(file.getName().contains("new_recording"));
    }
}
