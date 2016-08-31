package test;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.recorder.IVideoRecorder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.automation.remarks.video.recorder.VideoRecorder.conf;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

/**
 * Created by sepi on 19.07.16.
 */
public class TestFFmpegVideoRecorder extends BaseTest {

    private static final String VIDEO_FILE_NAME = "video_test";
    private static final String VIDEO_FOLDER_NAME = "video";


    @BeforeClass
    public static void setUpRecorder() {
        conf().withRecorderType(RecorderType.FFMPEG);
    }

    @Test
    public void shouldBeVideoFileInFolder() throws InterruptedException {
        File video = recordVideo();
        waitWhileVideoComplete(video);
        assertTrue("File doesn't exists " + video.getAbsolutePath(), video.exists());
    }

    @Test
    public void shouldBeVideoInRecordingsFolder() throws IOException, InterruptedException {
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
        File video = recordVideo();
        waitWhileVideoComplete(video);
        assertTrue("File doesn't exists " + video.getAbsolutePath(), video.exists());
        assertThat(video.getName(), startsWith(VIDEO_FILE_NAME));
    }

    private void waitWhileVideoComplete(File video) {
        await().atMost(120, TimeUnit.SECONDS)
                .pollDelay(10, TimeUnit.MILLISECONDS)
                .ignoreExceptions()
                .until(() -> video.exists());
    }

    private File recordVideo() throws InterruptedException {
        IVideoRecorder recorder = RecorderFactory.getRecorder();
        recorder.start();
        Thread.sleep(5000);
        return recorder.stopAndSave(VIDEO_FILE_NAME);
    }
}
