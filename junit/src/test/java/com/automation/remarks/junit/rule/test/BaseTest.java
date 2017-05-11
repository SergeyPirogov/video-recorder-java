package com.automation.remarks.junit.rule.test;

import com.automation.remarks.video.recorder.monte.MonteRecorder;

import java.io.File;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sepi on 15.02.17.
 */
public class BaseTest {

  protected void verifyVideoFileExistsWithName(String fileName) {
    File file = MonteRecorder.getLastRecording();
    assertTrue(file.exists());
    assertThat(file.getName(), startsWith(fileName));
  }

  protected void verifyVideoFileNotExists() {
    assertFalse(MonteRecorder.getLastRecording().exists());
  }

}
