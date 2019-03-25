package com.automation.remarks.testng.test;

import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.openqa.grid.selenium.GridLauncherV3;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.openqa.selenium.net.PortProber.findFreePort;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by sergey on 18.06.16.
 */
public class TestNGRemoteListenerTest extends BaseTest {

  @BeforeClass
  public static void runGrid() throws Exception {
    int port = findFreePort();
    startGrid(findFreePort(), port);
    System.setProperty("remote.video.hub", "http://localhost:" + port);
  }

  @BeforeMethod
  @AfterMethod
  public void resetConfig() {
    System.clearProperty("video.folder");
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
    int port = findFreePort();
    startGrid(findFreePort(), port);
    System.setProperty("remote.video.hub", "http://localhost:" + port);
    ITestResult result = prepareMock(testMethod);
    RemoteVideoListener listener = new RemoteVideoListener();
    listener.onTestStart(result);
    listener.onTestFailure(result);
    File file = MonteRecorder.getLastRecording();
    assertTrue(file.exists(), "File " + file.getName());
    assertThat(file.getName(), startsWith("shouldPassIfGridConfiguredWithCustomPorts"));
  }

  @Test
  public void shouldBeVideoForMethodWithoutAnnotationIfModeAll() {
    System.setProperty("video.mode", "ALL");
    ITestResult result = prepareMock(testMethod);
    RemoteVideoListener listener = new RemoteVideoListener();
    listener.onTestStart(result);
    listener.onTestFailure(result);
    File file = MonteRecorder.getLastRecording();
    assertTrue(file.exists(), "File " + file.getName());
  }

  @Test
  @Video
  public void shouldBeDefaultFolderForVideo() {
    ITestResult result = prepareMock(testMethod);
    RemoteVideoListener listener = new RemoteVideoListener();
    listener.onTestStart(result);
    listener.onTestFailure(result);
    File file = MonteRecorder.getLastRecording();
    assertThat(file.getParentFile().getName(), equalTo("video"));
  }

  @Test
  @Video
  public void shouldBeCustomFolderForVideo() {
  System.setProperty("video.folder", System.getProperty("user.dir") + "/custom_folder");
    ITestResult result = prepareMock(testMethod);
    RemoteVideoListener listener = new RemoteVideoListener();
    listener.onTestStart(result);
    listener.onTestFailure(result);
    File file = MonteRecorder.getLastRecording();
    assertThat(file.getParentFile().getName(), equalTo("custom_folder"));
  }

  private static void startGrid(int hubPort, int nodePort) throws Exception {
    String[] hub = {"-port", "" + hubPort,
        "-host", "localhost",
        "-role", "hub"};

    GridLauncherV3.main(hub);

    String[] node = {"-port", "" + nodePort,
        "-host", "localhost",
        "-role", "node",
        "-hub", "http://localhost:" + hubPort + "/grid/register",
        "-servlets", "com.automation.remarks.remote.node.Video"};
    GridLauncherV3.main(node);
    Thread.sleep(1000);
  }
}