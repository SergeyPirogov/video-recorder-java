package com.automation.remarks.testng;

interface IVideoRecordClient {
  void start();

  String stopAndSave(String fileName, boolean isTestSuccessful);
}
