package com.automation.remarks.junit5.test;

import com.automation.remarks.video.recorder.monte.MonteRecorder;

import java.io.File;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
