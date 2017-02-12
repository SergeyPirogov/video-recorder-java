package com.automation.remarks.junit;

import com.automation.remarks.junit5.Video;
import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.TestExtensionContext;

import java.io.File;
import java.lang.reflect.Method;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;
import static com.automation.remarks.video.enums.RecordingMode.ALL;

/**
 * Created by sergey on 12.02.17.
 */
public class VideoExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

  private IVideoRecorder recorder;

  @Override
  public void beforeTestExecution(TestExtensionContext context) throws Exception {
    if (videoDisabled(context.getTestMethod().get())) {
      return;
    }
    recorder = RecorderFactory.getRecorder(VideoRecorder.conf().getRecorderType());
    recorder.start();
  }

  @Override
  public void afterTestExecution(TestExtensionContext context) throws Exception {
    if (videoDisabled(context.getTestMethod().get())) {
      return;
    }

    String fileName = getFileName(context.getTestMethod().get());
    File video = stopRecording(fileName);
    if (context.getTestException().isPresent()) {
      doVideoProcessing(false, video);
    } else {
      doVideoProcessing(true, video);
    }
  }

  private boolean videoDisabled(Method testMethod) {
    return !videoEnabled(testMethod.getAnnotation(Video.class));
  }

  private String getFileName(Method testMethod) {
    String methodName = testMethod.getName();
    Video video = testMethod.getAnnotation(Video.class);
    return getVideoFileName(video, methodName);
  }

  private static boolean videoEnabled(Video video) {
    return VideoRecorder.conf().isVideoEnabled()
        && (VideoRecorder.conf().getMode().equals(ALL)
        || video != null);
  }

  private static String getVideoFileName(Video annotation, String methodName) {
    if (annotation == null) {
      return methodName;
    }
    String name = annotation.name();
    return name.length() > 1 ? name : methodName;
  }

  private File stopRecording(String filename) {
    if (recorder != null) {
      return recorder.stopAndSave(filename);
    }
    return null;
  }
}
