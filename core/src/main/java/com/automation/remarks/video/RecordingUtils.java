package com.automation.remarks.video;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.log4j.Logger;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

  private static final Logger logger = Logger.getLogger(RecordingUtils.class);

  private RecordingUtils() {
  }

  public static String doVideoProcessing(boolean successfulTest, File video) {
    String filePath = formatVideoFilePath(video);
    if (!successfulTest || isSaveAllModeEnable()) {
      logger.info("Video recording: " + filePath);
      return filePath;
    } else if (video != null && video.isFile()) {
      if (!video.delete()) {
        logger.info("Video didn't deleted");
        return "Video didn't deleted";
      }
      logger.info("No video on success test");
    }
    return "";
  }

  private static boolean isSaveAllModeEnable() {
    return VideoRecorder.conf().saveMode().equals(VideoSaveMode.ALL);
  }

  public static boolean videoEnabled(Video video) {
    return VideoRecorder.conf().videoEnabled()
        && (isRecordingAllModeEnable() || video != null);
  }

  private static boolean isRecordingAllModeEnable() {
    return VideoRecorder.conf().mode().equals(RecordingMode.ALL);
  }

  public static String getVideoFileName(Video annotation, String methodName) {
    if (useNameFromVideoAnnotation(annotation) && VideoRecorder.conf().fileName() != null) {
      return VideoRecorder.conf().fileName();
    } else if (annotation == null) {
      return methodName;
    }
    String name = annotation.name();
    return name.length() > 1 ? name : methodName;
  }

  private static boolean useNameFromVideoAnnotation(Video annotation) {
    return annotation == null || annotation.name().isEmpty();
  }

  private static String formatVideoFilePath(File video) {
    if (video == null) {
      return "";
    }
    String jenkinsReportsUrl = getJenkinsReportsUrl();
    if (!isBlank(jenkinsReportsUrl)) {
      return jenkinsReportsUrl + video.getName();
    }
    return video.getAbsolutePath();
  }

  private static String getJenkinsReportsUrl() {
    String jenkinsUrl = System.getProperty("jenkinsUrl");
    if (!isBlank(jenkinsUrl)) {
      return jenkinsUrl;
    } else {
      logger.info("No jenkinsUrl variable found.");
      return "";
    }
  }
}
