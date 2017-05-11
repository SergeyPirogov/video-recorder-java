package com.automation.remarks.junit.rule.test;

import com.automation.remarks.junit.VideoExtension;
import com.automation.remarks.junit5.Video;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.TestExtensionContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sergey on 12.02.17.
 */
public class Junit5VideoTest extends BaseTest {


  @BeforeEach
  public void setUp() throws IOException {
    FileUtils.deleteDirectory(MonteRecorder.conf().getVideoFolder());
  }

  @Video
  public void testVideo() throws InterruptedException {
    Thread.sleep(5000);
    fail();
  }

  public void testWithoutAnnotation() throws InterruptedException {
    Thread.sleep(5000);
    fail();
  }

  @Video
  public void successTest() throws InterruptedException {
    Thread.sleep(5000);
  }

  @Test
  void shouldBeVideoForAnnotatedMethod() throws Exception {
    TestExtensionContext context = prepareMock("testVideo", new AssertionError());

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileExistsWithName("testVideo");
  }

  @Test
  void shouldNotBeVideoForMethodWithoutAnnotation() throws Exception {
    TestExtensionContext context = prepareMock("testWithoutAnnotation", new AssertionError());

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileNotExists();
  }

  @Test
  void shouldNotBeVideoForSuccessTest() throws Exception {
    TestExtensionContext context = prepareMock("successTest");

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileNotExists();
  }

  private TestExtensionContext prepareMock(String testMethodName, Throwable ex) throws NoSuchMethodException {
    Method method = this.getClass().getMethod(testMethodName);
    TestExtensionContext context = mock(TestExtensionContext.class);

    when(context.getTestMethod()).thenReturn(Optional.ofNullable(method));
    when(context.getTestException()).thenReturn(Optional.ofNullable(ex));
    return context;
  }

  private TestExtensionContext prepareMock(String testMethodName) throws NoSuchMethodException {
    return prepareMock(testMethodName, null);
  }
}


