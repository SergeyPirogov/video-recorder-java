package com.automation.remarks.testng;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.testng.ITestResult;

import java.util.List;

import static com.automation.remarks.testng.utils.ListenerUtils.getFileName;
import static com.automation.remarks.testng.utils.MethodUtils.getVideoAnnotation;
import static com.automation.remarks.video.RecordingUtils.videoEnabled;

/**
 * Created by moonkin on 19.01.19
 */
public class UniversalVideoListener extends TestNgListener {

  private IVideoRecordClient videoRecordClient;

  @Override
  public void onTestStart(ITestResult result) {
    if (videoDisabled(result) || shouldNotIntercept(result)) {
      return;
    }
    if (VideoRecorder.conf().isRemote()) {
      String nodeUrl = VideoRecorder.conf().remoteUrl();
      videoRecordClient = new RemoteVideoRecordClient(nodeUrl);
    } else {
      videoRecordClient = new LocalVideoRecordClient();
    }
    videoRecordClient.start();
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    if (videoDisabled(result) || shouldNotIntercept(result)) {
      return;
    }
    String fileName = getFileName(result);
    videoRecordClient.stopAndSave(fileName, true);
  }

  @Override
  public void onTestFailure(ITestResult result) {
    if (videoDisabled(result) || shouldNotIntercept(result)) {
      return;
    }
    String fileName = getFileName(result);
    videoRecordClient.stopAndSave(fileName, false);
  }

  private boolean videoDisabled(ITestResult result) {
    return !videoEnabled(getVideoAnnotation(result));
  }

  public boolean shouldNotIntercept(ITestResult result) {
    List<String> listeners = result.getTestContext().getCurrentXmlTest().getSuite().getListeners();
    return !listeners.contains(this.getClass().getName()) && !shouldIntercept(result.getTestClass().getRealClass(), this.getClass());
  }
}
