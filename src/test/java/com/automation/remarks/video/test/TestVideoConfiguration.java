package com.automation.remarks.video.test;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by sergey on 5/3/16.
 */
public class TestVideoConfiguration {

    @Test
    public void shouldDisableVideoRecording() throws Exception {
        System.setProperty("video.enabled", "false");
        VideoRecorder recorder = new VideoRecorder("test_video");
        recorder.start();
        LinkedList<File> files = recorder.stop();
        assertEquals(files.size(), 0);
    }
}
