package test;

import com.automation.remarks.video.recorder.VideoConfiguration;
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
        VideoRecorder.conf().videoEnabled(false);
        VideoRecorder recorder = new VideoRecorder("test_video");
        recorder.start();
        LinkedList<File> files = recorder.stop();
        assertEquals(files.size(), 0);
    }

    @Test
    public void testName() throws Exception {
        VideoConfiguration conf = VideoRecorder.conf();
        conf.videoEnabled(true);
        final String path = System.getProperty("user.dir") + File.separator + "video";
        conf.withVideoFolder(path);
        VideoRecorder recorder = new VideoRecorder("test_video");
        recorder.start();
        LinkedList<File> files = recorder.stop();
        String canonicalPath = files.getFirst().getCanonicalPath();
        assertThat(canonicalPath, CoreMatchers.startsWith(path));
    }
}
