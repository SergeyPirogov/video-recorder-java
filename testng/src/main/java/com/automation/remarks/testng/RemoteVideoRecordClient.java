package com.automation.remarks.testng;

import com.automation.remarks.video.recorder.VideoRecorder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.automation.remarks.testng.utils.RestUtils.sendRecordingRequest;

public class RemoteVideoRecordClient implements IVideoRecordClient {

  private String servletUrl;

  public RemoteVideoRecordClient(String nodeUrl) {
    String servletPath = "/extra/Video";
    servletUrl = nodeUrl + servletPath;
  }

  @Override
  public void start() {
    String folderUrl = encodeFilePath(new File(VideoRecorder.conf().folder()));
    String url = servletUrl + "/start?&folder=" + folderUrl;
    sendRecordingRequest(url);
  }

  @Override
  public String stopAndSave(String testName, boolean isSuccess) {
    String url = servletUrl + "/stop?result=" + isSuccess + "&name=" + testName;
    return getFilePathFromResponse(url);
  }

  private String getFilePathFromResponse(String url) {
    return sendRecordingRequest(url).replace("recording stopped ", "");
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
