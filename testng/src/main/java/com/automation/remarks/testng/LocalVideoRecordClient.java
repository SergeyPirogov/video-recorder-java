package com.automation.remarks.testng;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;

import static com.automation.remarks.video.RecordingUtils.doVideoProcessing;

/**
 * Created by moonkin on 19.01.19
 */
public class LocalVideoRecordClient implements IVideoRecordClient {

  private IVideoRecorder recorder;

  public void start() {
    recorder = RecorderFactory.getRecorder(VideoRecorder.conf().recorderType());
    recorder.start();
  }

  public String stopAndSave(String filename, boolean isTestSuccessful) {
    if (recorder != null) {
      return doVideoProcessing(isTestSuccessful, recorder.stopAndSave(filename));
    }
    return null;
  }
}
