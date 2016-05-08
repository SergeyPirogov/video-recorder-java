package test;

import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.recorder.VideoRecorder;
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
    public static final String VIDEO_FOLDER_NAME = "video";

    public LinkedList<File> recordVideo() {
        VideoRecorder recorder = new VideoRecorder(VIDEO_FILE_NAME);
        recorder.start();
        return recorder.stop();
    }

    @Test
    public void shouldBeListWithOneVideo() {
        LinkedList<File> files = recordVideo();
        assertEquals(files.size(), 1);
    }

    @Test
    public void shouldBeVideoInRecordingsFolder() throws IOException {
        VideoConfiguration.VIDEO_FOLDER = VIDEO_FOLDER_NAME;
        File video = recordVideo().getFirst();
        String folderName = video.getParentFile().getName();
        assertEquals(folderName, VIDEO_FOLDER_NAME);
    }

    @Test
    public void shouldBeExactVideoFileName() throws Exception {
        String fileName = recordVideo().getFirst().getName();
        assertThat(fileName, startsWith(VIDEO_FILE_NAME));
    }
}