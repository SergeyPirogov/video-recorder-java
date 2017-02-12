package com.automation.remarks.junit.rule.test;

import com.automation.remarks.junit5.Video;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.fail;

/**
 * Created by sergey on 12.02.17.
 */
public class Junit5VideoTest {

  @Test
  @Video
  void testVideo() throws InterruptedException {
     Thread.sleep(5000);
     fail();
  }
}
