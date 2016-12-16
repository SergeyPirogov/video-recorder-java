package com.automation.remarks.testng.test;

import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.openqa.grid.selenium.GridLauncherV3;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGRemoteListenerTest extends BaseTest {

    @BeforeClass
    public static void runGrid() throws Exception {
        startGrid("4444","5555");
    }

    @Test
    @Video
    public void shouldBeOneRecordingOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists());
    }

    @Test
    @Video
    public void shouldNotBeRecordingOnTestSuccess() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestSuccess(result);
        File file = MonteRecorder.getLastRecording();
        assertFalse(file.exists());
    }

    @Test
    @Video(name = "custom_name")
    public void shouldBeRecordingWithCustomNameOnTestFail() {
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists(), "File " + file.getName());
        assertThat(file.getName(), startsWith("custom_name"));
    }

    @Test
    @Video
    public void shouldPassIfGridConfiguredWithCustomPorts() throws Exception {
        startGrid("4446","5556");
        MonteRecorder.conf().withRemoteGridHubUrl("http://localhost:4446");
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists(), "File " + file.getName());
        assertThat(file.getName(), startsWith("shouldPassIfGridConfiguredWithCustomPorts"));
    }

    @Test
    public void shouldBeVideoForMethodWithoutAnnotationIfModeAll(){
        MonteRecorder.conf().withRecordMode(RecordingMode.ALL);
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertTrue(file.exists(), "File " + file.getName());
    }

    @Test
    @Video
    public void shouldBeDefaultFolderForVideo(){
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertThat(file.getParentFile().getName(), equalTo("video"));
    }

    @Test
    @Video
    public void shouldBeCustomFolderForVideo(){
        MonteRecorder.conf().withVideoFolder("custom_folder");
        ITestResult result = prepareMock(testMethod);
        RemoteVideoListener listener = new RemoteVideoListener();
        listener.onTestStart(result);
        listener.onTestFailure(result);
        File file = MonteRecorder.getLastRecording();
        assertThat(file.getParentFile().getName(), equalTo("custom_folder"));
    }

    private static void startGrid(String hubPort, String nodePort) throws Exception {
        String[] hub = {"-port", hubPort,
                "-host", "localhost",
                "-role", "hub"};
        GridLauncherV3.main(hub);

        String[] node = {"-port", nodePort,
                "-host", "localhost",
                "-role", "node",
                "-hub", "http://localhost:" + hubPort + "/grid/register",
                "-servlets", "com.automation.remarks.remote.node.Video"};
        GridLauncherV3.main(node);
        Thread.sleep(1000);
    }
}