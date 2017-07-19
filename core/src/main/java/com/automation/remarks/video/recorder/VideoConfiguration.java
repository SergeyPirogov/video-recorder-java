package com.automation.remarks.video.recorder;

import com.automation.remarks.video.SystemUtils;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.enums.RecordingMode;
import com.automation.remarks.video.enums.VideoSaveMode;
import org.aeonbits.owner.Config;

import java.awt.*;
import java.io.File;

/**
 * Created by sergey on 4/13/16.
 */
@Config.Sources("classpath:video.properties")
public interface VideoConfiguration extends Config {

  @Key("video.folder")
  default String folder() {
    return System.getProperty("user.dir") + File.separator + "video";
  }

  @Key("video.enabled")
  @DefaultValue("true")
  Boolean videoEnabled();

  @Key("video.mode")
  @DefaultValue("ANNOTATED")
  RecordingMode mode();

  @DefaultValue("http://localhost:4444")
  @Key("remote.video.hub")
  String remoteUrl();

  @Key("recorder.type")
  @DefaultValue("MONTE")
  RecorderType recorderType();

  @Key("video.save.mode")
  @DefaultValue("FAILED_ONLY")
  VideoSaveMode saveMode();

  @DefaultValue("24")
  @Key("video.frame.rate")
  int frameRate();

  @Key("video.screen.size")
  default Dimension screenSize() {
    return SystemUtils.getSystemScreenDimension();
  }
}
