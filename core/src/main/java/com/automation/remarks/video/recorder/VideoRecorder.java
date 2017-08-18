package com.automation.remarks.video.recorder;

import org.aeonbits.owner.ConfigFactory;

import java.io.File;

import static com.automation.remarks.video.SystemUtils.getOsType;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class VideoRecorder implements IVideoRecorder {
  public static VideoConfiguration conf() {
    ConfigFactory.setProperty("os.type", getOsType());
    return ConfigFactory.create(VideoConfiguration.class, System.getProperties());
  }

  private static File lastVideo;

  protected void setLastVideo(File video) {
    lastVideo = video;
  }

  public static File getLastRecording() {
    return lastVideo;
  }
}
