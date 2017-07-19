package com.automation.remarks.testng.test;

import com.automation.remarks.testng.GridInfoExtractor;
import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.net.URL;

import static com.automation.remarks.testng.test.TestNGRemoteListenerTest.startGrid;
import static org.testng.Assert.fail;

/**
 * Created by sergey on 09.01.17.
 */
@Listeners(RemoteVideoListener.class)
public class IntegrationTest {

  RemoteWebDriver driver;

  @BeforeClass
  public void setUp() throws Exception {
    startGrid("4444", "5555");
    Configuration.browser = "chrome";

    ChromeDriverManager.getInstance().setup();
    URL hubUrl = new URL("http://localhost:4444/wd/hub");

    driver = new RemoteWebDriver(hubUrl, DesiredCapabilities.chrome());

    String nodeIp = GridInfoExtractor.getNodeIp(hubUrl, driver.getSessionId().toString());

    System.setProperty("remote.video.hub", nodeIp);
  }

  @Test
  @Video
  public void test() throws InterruptedException {
    driver.get("http://automation-remarks.com");
    Thread.sleep(5000);
    fail();
  }

  @Test
  @Video
  public void test2() throws InterruptedException {
    driver.get("http://spirogov.github.io");
    Thread.sleep(5000);
  }
}
