package com.automation.remarks.testng.test;

import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.hamcrest.CoreMatchers;
import org.openqa.grid.selenium.GridLauncher;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGRemoteListenerTest extends BaseTest{

    @BeforeClass
    public static void runGrid() throws Exception {
        String[] hub = {"-port", "4444",
                "-host", "localhost",
                "-role", "hub",
                "-servlets", "com.automation.remarks.remote.hub.Video"};
        GridLauncher.main(hub);

        String[] node = {"-port", "5555",
                "-host", "localhost",
                "-role", "node",
                "-hub", "http://localhost:4444/grid/register",
                "-servlets", "com.automation.remarks.remote.node.VideoServlet"};
        GridLauncher.main(node);
        Thread.sleep(1000);
    }

    @Test
    @Video
    public void shouldBeOneRecordingOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists());
    }

    @Test
    @Video(name = "custom_name")
    public void shouldBeRecordingWithCustomNameOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists(), "File " + file.getName());
        assertThat(file.getName(), CoreMatchers.startsWith("custom_name"));
    }

    @Test
    @Video(enabled = false)
    public void shouldNotBeRecordingIfVideoEnabledIsFalse() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertFalse(file.exists(), "File " + file.getName());
    }
}