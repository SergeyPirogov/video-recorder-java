package com.autmation.remarks.video.test;

import com.automation.remarks.junit.VideoRule;
import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.autmation.remarks.video.test.util.TestUtils.runRule;
import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sergey on 4/21/16.
 */
public class JUnitVideoRecording {

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(VideoConfiguration.VIDEO_FOLDER));
    }

    @Video
    public void failWithVideo() throws Exception {
        fail();
    }

    @Video(name = "custom_name")
    public void failWithCustomVideoName() throws Exception {
        fail();
    }

    @Video(enabled = false)
    public void failWithoutVideo() {
        fail();
    }

    @Test
    public void shouldPassOnMethodWithVideoAnnotation() {
        String methodName = "failWithVideo";
        VideoRule videoRule = new VideoRule();
        runRule(videoRule, this.getClass(), methodName);
        verifyVideoFileExistsWithName(methodName);
    }

    @Test
    public void shouldPassOnMethodWithVideoAnnotationAndCustomName() {
        String methodName = "failWithCustomVideoName";
        String video_name = "custom_name";
        VideoRule videoRule = new VideoRule();
        runRule(videoRule, this.getClass(), methodName);
        verifyVideoFileExistsWithName(video_name);
    }

    @Test
    public void shouldPassOnMethodWithoutVideoIfVideoEnableFlagIsFalse() {
        String methodName = "failWithoutVideo";
        VideoRule videoRule = new VideoRule();
        runRule(videoRule, this.getClass(), methodName);
        verifyVideoFileNotExists();
    }

    private void verifyVideoFileExistsWithName(String fileName) {
        File file = new File(VideoRecorder.getLastRecordingPath());
        assertTrue(file.exists());
        assertThat(file.getName(), startsWith(fileName));
    }

    private void verifyVideoFileNotExists() {
        assertFalse(new File(VideoRecorder.getLastRecordingPath()).exists());
    }
}
