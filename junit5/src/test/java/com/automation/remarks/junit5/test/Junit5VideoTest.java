package com.automation.remarks.junit5.test;

import com.automation.remarks.junit5.Video;
import com.automation.remarks.junit5.VideoExtension;
import com.automation.remarks.video.recorder.monte.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * Created by sergey on 12.02.17.
 */
public class Junit5VideoTest extends BaseTest {


  @BeforeEach
  public void setUp() throws IOException {
    FileUtils.deleteDirectory(new File(MonteRecorder.conf().folder()));
  }

  @Video
  public void testVideo() throws InterruptedException {
    Thread.sleep(5000);
    fail("");
  }

  public void testWithoutAnnotation() throws InterruptedException {
    Thread.sleep(5000);
    fail("");
  }

  @Video
  public void successTest() throws InterruptedException {
    Thread.sleep(5000);
  }

  @Test
  void shouldBeVideoForAnnotatedMethod() throws Exception {
    ExtensionContext context = prepareMock("testVideo", new AssertionError());

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileExistsWithName("testVideo");
  }

  @Test
  void shouldNotBeVideoForMethodWithoutAnnotation() throws Exception {
    ExtensionContext context = prepareMock("testWithoutAnnotation", new AssertionError());

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileNotExists();
  }

  @Test
  void shouldNotBeVideoForSuccessTest() throws Exception {
    ExtensionContext context = prepareMock("successTest");

    VideoExtension ext = new VideoExtension();
    ext.beforeTestExecution(context);
    ext.afterTestExecution(context);
    verifyVideoFileNotExists();
  }

  private ExtensionContext prepareMock(String testMethodName, Throwable ex) throws NoSuchMethodException {
    Method method = this.getClass().getMethod(testMethodName);
    ExtensionContext context = Mockito.mock(ExtensionContext.class);

    Mockito.when(context.getTestMethod()).thenReturn(Optional.ofNullable(method));
    Mockito.when(context.getExecutionException()).thenReturn(Optional.ofNullable(ex));
    return context;
  }

  private ExtensionContext prepareMock(String testMethodName) throws NoSuchMethodException {
    return prepareMock(testMethodName, null);
  }
}


