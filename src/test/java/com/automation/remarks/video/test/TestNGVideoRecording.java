package com.automation.remarks.video.test;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.testng.VideoListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by sergey on 3/27/16.
 */
@Listeners(VideoListener.class)
public class TestNGVideoRecording {


    @Test
    public void first_test() throws Exception {
        Thread.sleep(1000);
        assertTrue(false);
    }

    @Test
    @Video(enabled = false)
    public void testName() throws Exception {
        Thread.sleep(1000);
        assertTrue(false);
    }

    @Test
    @Video(name = "second_test")
    public void testName2() throws Exception {
        Thread.sleep(1000);
        assertTrue(false);
    }

    @Test
    @Video
    public void testName3() throws Exception {
        Thread.sleep(1000);
        assertTrue(false);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        System.out.println("After Test");
    }
}
