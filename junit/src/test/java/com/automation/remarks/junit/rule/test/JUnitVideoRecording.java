package com.automation.remarks.junit.rule.test;

import com.automation.remarks.junit.VideoRule;
import com.automation.remarks.junit.rule.test.util.TestUtils;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sergey on 4/21/16.
 */
public class JUnitVideoRecording {

  @Video
  public void failWithVideo() throws Exception {
    fail();
  }

  @Video(name = "custom_name")
  public void failWithCustomVideoName() throws Exception {
    fail();
  }

  @Video
  public void successWithVideo() {
  }

  @Test
  public void shouldPassOnMethodWithVideoAnnotation() {
    String methodName = "failWithVideo";
    VideoRule videoRule = new VideoRule();
    TestUtils.runRule(videoRule, this, methodName);
    verifyVideoFileExistsWithName(methodName);
  }

  @Test
  public void shouldPassOnMethodWithVideoAnnotationAndCustomName() {
    String methodName = "failWithCustomVideoName";
    String video_name = "custom_name";
    VideoRule videoRule = new VideoRule();
    TestUtils.runRule(videoRule, this, methodName);
    verifyVideoFileExistsWithName(video_name);
  }

  @Test
  public void shouldPassOnSuccessTestWithVideoIfSaveModeAll() {
    String methodName = "successWithVideo";

    System.setProperty("video.save.mode", "ALL");

    VideoRule videoRule = new VideoRule();
    TestUtils.runRule(videoRule, this, methodName);
    verifyVideoFileExistsWithName(methodName);
  }

  @Test
  public void shouldPassOnSuccessTestWithoutVideoIfSaveModeFailedOnly() {
    String methodName = "successWithVideo";
    System.setProperty("video.save.mode", "FAILED_ONLY");
    VideoRule videoRule = new VideoRule();
    TestUtils.runRule(videoRule, this, methodName);
    verifyVideoFileNotExists();
  }

  @Before
  public void setUp() throws IOException {
    FileUtils.deleteDirectory(new File(MonteRecorder.conf().folder()));
  }

  @After
  public void tearDown() throws Exception {
    setUp();
  }

  private void verifyVideoFileExistsWithName(String fileName) {
    File file = MonteRecorder.getLastRecording();
    assertTrue(file.exists());
    assertThat(file.getName(), startsWith(fileName));
  }

  private void verifyVideoFileNotExists() {
    assertFalse(MonteRecorder.getLastRecording().exists());
  }


}
