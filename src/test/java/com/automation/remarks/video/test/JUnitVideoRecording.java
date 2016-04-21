package com.automation.remarks.video.test;

import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.junit.VideoRule;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by sergey on 4/21/16.
 */
public class JUnitVideoRecording {

    @Rule
    public VideoRule videoRule = new VideoRule();

    @Test
    public void shouldPass() throws Exception {
        Thread.sleep(5000);
    }

    @Test
    @Video(name = "second_test", enabled = false)
    public void shouldFail() throws Exception {
        Thread.sleep(5000);
        assertTrue(false);
    }
}
