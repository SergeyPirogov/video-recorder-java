package com.automation.remarks.testng;

import com.automation.remarks.video.recorder.VideoRecorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;

/**
 * Created by sepi on 16.12.16.
 */
public class RemoteVideoClient {

  private String servletUrl;

  public RemoteVideoClient(String nodeUrl) {
    String servletPath = "/extra/Video";
    this.servletUrl = nodeUrl + servletPath;
  }

  public void videoStart() {
    String folderUrl = encodeFilePath(new File(VideoRecorder.conf().folder()));
    String url = servletUrl + "/start?&folder=" + folderUrl;
    sendRecordingRequest(url);
  }

  public void videoStop(String testName, boolean isSuccess) {
    String url = servletUrl + "/stop?result=" + isSuccess + "&name=" + testName;
    sendRecordingRequest(url);
  }

  private String encodeFilePath(File file) {
    URL videoFolder = null;
    try {
      videoFolder = file.toURI().toURL();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return videoFolder != null ? videoFolder.toString().replace("file:", "") : null;
  }

}
