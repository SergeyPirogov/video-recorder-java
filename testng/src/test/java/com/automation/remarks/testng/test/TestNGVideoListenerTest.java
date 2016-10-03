package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

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
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists());
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = MonteRecorder.getLastRecording();
        assertFalse(file.exists());
    }

    @Test
    @Video(name = "new_recording")
    public void shouldBeRecordingWithCustomName() {
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists());
        assertTrue(file.getName().contains("new_recording"));
    }

    @Test
    @Video()
    public void shouldBeRecordingForSuccessfulTestAndSaveModeAll() {
        MonteRecorder.conf().withVideoSaveMode(VideoSaveMode.ALL);
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists());
        assertTrue(file.getName().contains("shouldBeRecordingForSuccessfulTest"), "Wrong file name");
    }

    @Test
    @Video()
    public void shouldBeRecordingForFailedTestAndSaveModeFailOnly() {
        MonteRecorder.conf().withVideoSaveMode(VideoSaveMode.FAILED_ONLY);
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists());
        assertTrue(file.getName().contains("shouldBeRecordingForFailedTestAndSaveModeFailOnly"), "Wrong file name");
    }

    @Test
    @Video()
    public void shouldNotBeRecordingForSuccessfulTestAndSaveModeFailOnly() {
        MonteRecorder.conf().withVideoSaveMode(VideoSaveMode.FAILED_ONLY);
        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = MonteRecorder.getLastRecording();
        assertFalse(file.exists());
    }

    @Test
    @Video()
    public void shouldNotBeVideoIfDisabledAndRecordModeAll() {
        MonteRecorder.conf()
                .videoEnabled(false)
                .withRecordMode(RecordingMode.ALL);

        ITestResult result = prepareMock(testMethod);
        VideoListener listener = new VideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertNotEquals(file.getName(), "shouldNotBeVideoIfDisabledAndRecordModeAll.avi");
    }
}
