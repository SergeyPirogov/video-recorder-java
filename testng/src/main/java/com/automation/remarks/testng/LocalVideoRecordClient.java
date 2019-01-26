package com.automation.remarks.testng;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

public class LocalVideoRecordClient implements IVideoRecordClient {

  private IVideoRecorder recorder;

  @Override
  public void start() {
    recorder = RecorderFactory.getRecorder(VideoRecorder.conf().recorderType());
    recorder.start();
  }

  @Override
  public String stopAndSave(String filename, boolean isTestSuccessful) {
    if (recorder != null) {
      return doVideoProcessing(isTestSuccessful, recorder.stopAndSave(filename));
    }
    return null;
  }
}
