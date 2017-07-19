package com.automation.remarks.video.recorder;

import org.aeonbits.owner.ConfigFactory;

import java.io.File;

/**
 * Created by sepi on 19.07.16.
 */
public abstract class VideoRecorder implements IVideoRecorder {
  public static VideoConfiguration conf() {
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
