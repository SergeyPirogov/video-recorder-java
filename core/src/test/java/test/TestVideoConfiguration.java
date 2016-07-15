package test;

import com.automation.remarks.video.recorder.VideoConfiguration;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Created by sergey on 5/3/16.
 */
public class TestVideoConfiguration {

    @Test
    public void shouldDisableVideoRecording() throws Exception {
        VideoRecorder.conf().videoEnabled(false);
        VideoRecorder recorder = new VideoRecorder();
        recorder.start();
        File test_video = recorder.stopAndSave("test_video");
        assertNull(test_video);

    }

    @Test
    public void shouldBeVideoFileWithDefaultVideoFolderPath() throws Exception {
        VideoConfiguration conf = VideoRecorder.conf();
        conf.videoEnabled(true);
        final String path = System.getProperty("user.dir") + File.separator + "video";
        conf.withVideoFolder(path);
        VideoRecorder recorder = new VideoRecorder();
        recorder.start();
        File file = recorder.stopAndSave("test_video");
        String canonicalPath = file.getCanonicalPath();
        assertThat(canonicalPath, CoreMatchers.startsWith(path));
    }
}
