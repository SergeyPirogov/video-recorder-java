package com.automation.remarks.video.test;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

    @Test
    public void testName() throws Exception {
        final String path = "/home/sergey/VideoRecorder/video/";
        System.setProperty("video.folder", path);
        VideoRecorder recorder = new VideoRecorder("test_video");
        recorder.start();
        LinkedList<File> files = recorder.stop();
        String canonicalPath = files.getFirst().getCanonicalPath();
        assertThat(canonicalPath, CoreMatchers.startsWith(path));
    }
}
