package com.automation.remarks.video;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.enums.VideoSaveMode;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.log4j.Logger;

import java.io.File;

import static com.automation.remarks.video.enums.RecordingMode.ALL;
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
    if (!successfulTest || VideoRecorder.conf().saveMode().equals(VideoSaveMode.ALL)) {
      logger.info("Video recording: " + filePath);
      return filePath;
    } else if (video != null) {
      video.delete();
      logger.info("No video on success test");
    }
    return "";
  }

  public static boolean videoEnabled(Video video) {
    return VideoRecorder.conf().videoEnabled()
        && (VideoRecorder.conf().mode().equals(ALL)
        || video != null);
  }

  public static String getVideoFileName(Video annotation, String methodName) {
    if ((annotation == null || annotation.name().isEmpty()) && VideoRecorder.conf().fileName() != null) {
      return VideoRecorder.conf().fileName();
    } else if (annotation == null) {
      return methodName;
    }
    String name = annotation.name();
    return name.length() > 1 ? name : methodName;
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
    String jenkins_url = System.getProperty("jenkins_url");
    if (!isBlank(jenkins_url)) {
      return jenkins_url;
    } else {
      logger.info("No jenkins_url variable found.");
      return null;
    }
  }
}
