package test;

import com.automation.remarks.video.RecorderType;
import com.automation.remarks.video.recorder.FFMpegRecorder;
import com.automation.remarks.video.recorder.MonteRecorder;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.automation.remarks.video.recorder.BaseRecorder.conf;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by sepi on 19.07.16.
 */
public class TestFFmpegVideoRecorder extends BaseTest {

    private static final String VIDEO_FILE_NAME = "video_test";
    private static final String VIDEO_FOLDER_NAME = "video";

    private File recordVideo() {
        FFMpegRecorder recorder = new FFMpegRecorder();
        recorder.start();
        return recorder.stopAndSave(VIDEO_FILE_NAME);
    }

    @BeforeClass
    public static void setUpRecorder() {
        conf().withRecorderType(RecorderType.FFMPEG);
    }

    @Test
    public void shouldBeVideoFileInFolder() {
        File video = recordVideo();
        assertTrue("File doesn't exists " + video.getAbsolutePath(), video.exists());
    }

    @Test
    public void shouldBeVideoInRecordingsFolder() throws IOException {
        conf().withVideoFolder(VIDEO_FOLDER_NAME);
        File video = recordVideo();
        String folderName = video.getParentFile().getName();
        assertEquals(folderName, VIDEO_FOLDER_NAME);
    }

    @Test
    public void shouldBeAbsoluteRecordingPath() throws Exception {
        File video = recordVideo();
        assertThat(video.getAbsolutePath(), startsWith(conf().getVideoFolder().getAbsolutePath() + File.separator + VIDEO_FILE_NAME));
    }

    @Test
    public void shouldBeExactVideoFileName() throws Exception {
        String fileName = recordVideo().getName();
        assertThat(fileName, startsWith(VIDEO_FILE_NAME));
    }
}
