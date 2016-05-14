package com.automation.remarks.remote.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.util.logging.Logger;

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class GridTest {

    private static final Logger LOGGER = Logger.getLogger(GridTest.class.getName());

    @Before
    public void setUp() throws Exception {
        Configuration.remote = "http://localhost:4444/wd/hub";
        sendRecordingRequest("http://127.0.0.1:4444/grid/admin/Video/start");
    }

    @Test
    public void test1() throws Exception {
        Selenide.open("http://google.com.ua");
        Thread.sleep(5000);
    }

    @Test
    public void test2() throws Exception {
        Selenide.open("http://google.com.ua");
        Thread.sleep(5000);
    }

    @After
    public void tearDown() throws Exception {
        sendRecordingRequest("http://127.0.0.1:4444/grid/admin/Video/stop");
    }
}
