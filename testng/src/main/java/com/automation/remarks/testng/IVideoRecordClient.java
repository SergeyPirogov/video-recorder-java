package com.automation.remarks.testng;

/**
 * Created by moonkin on 19.01.19
 */
interface IVideoRecordClient {
  void start();

  String stopAndSave(String fileName, boolean isTestSuccessful);

}
