package com.automation.remarks.testng.test;

import com.automation.remarks.testng.RemoteVideoListener;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.automation.remarks.testng.test.TestNGRemoteListenerTest.startGrid;
import static org.testng.Assert.fail;

/**
 * Created by sergey on 09.01.17.
 */
@Listeners(RemoteVideoListener.class)
public class IntegrationTest {
    @BeforeClass
    public void setUp() throws Exception {
        Configuration.browser = "chrome";
        startGrid("4444","5555");
        VideoRecorder.conf()
                .withRemoteUrl("http://localhost:5555");
    }

    @Test
    @Video
    public void test() throws InterruptedException {
        Thread.sleep(5000);
        fail();
    }
}
