package com.automation.remarks.testng.test;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by sergey on 08.05.16.
 */
@Listeners(VideoListener.class)
public class TestNgVideoRecordingTest {

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
