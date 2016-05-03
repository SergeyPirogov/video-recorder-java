package com.automation.remarks.video.test;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by sergey on 5/3/16.
 */
public class TestVideoRecorder {

    public static final String VIDEO_FILE_NAME = "video_test";
    public static final String VIDEO_FOLDER_NAME = "recordings";

    static LinkedList<File> files;

    @BeforeClass
    public static void recordVideo() {
        VideoRecorder recorder = new VideoRecorder(VIDEO_FILE_NAME);
        recorder.start();
        files = recorder.stop();
    }

    @Test
    public void shouldBeListWithOneVideo() {
        assertEquals(files.size(), 1);
    }

    @Test
    public void shouldBeVideoInRecordingsFolder() throws IOException {
        File video = files.getFirst();
        String folderName = video.getParentFile().getName();
        assertEquals(folderName, VIDEO_FOLDER_NAME);
    }

    @Test
    public void shouldBeExactVideoFileName() throws Exception {
        String fileName = files.getFirst().getName();
        assertThat(fileName, startsWith(VIDEO_FILE_NAME));
    }
}