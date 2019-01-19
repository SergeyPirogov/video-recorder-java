package com.automation.remarks.testng;

import com.automation.remarks.video.recorder.VideoRecorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;

/**
 * Created by moonkin on 19.01.19.
 */
public class RemoteVideoRecordClient implements IVideoRecordClient {

  private String servletUrl;

  public RemoteVideoRecordClient(String nodeUrl) {
    String servletPath = "/extra/Video";
    this.servletUrl = nodeUrl + servletPath;
  }

  public void start() {
    String folder_url = encodeFilePath(new File(VideoRecorder.conf().folder()));
    String url = servletUrl + "/start?&folder=" + folder_url;
    sendRecordingRequest(url);
  }

  public String stopAndSave(String testName, boolean isSuccess) {
    String url = servletUrl + "/stop?result=" + isSuccess + "&name=" + testName;
    return sendRecordingRequest(url);
  }

  private String encodeFilePath(File file) {
    URL videoFolder = null;
    try {
      videoFolder = file.toURI().toURL();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return videoFolder.toString().replace("file:", "");
  }

}
