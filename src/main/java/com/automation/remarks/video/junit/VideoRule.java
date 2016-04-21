package com.automation.remarks.video.junit;

import com.automation.remarks.video.VideoConfiguration;
import com.automation.remarks.video.annotations.Video;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.util.LinkedList;

import static com.automation.remarks.video.RecordingUtils.deleteRecordingOnSuccess;

/**
 * Created by sergey on 4/21/16.
 */
public class VideoRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                boolean successful = false;
                Video video = description.getAnnotation(Video.class);
                String name = description.getMethodName();
                if (video != null) {
                    VideoConfiguration.VIDEO_ENABLED = Boolean.toString(video.enabled());
                    name = getName(video, description);
                }

                VideoRecorder recorder = new VideoRecorder(name);
                recorder.start();
                try {
                    base.evaluate();
                    successful = true;
                } finally {
                    LinkedList<File> files = recorder.stop();
                    if (successful) {
                        deleteRecordingOnSuccess(files);
                    }
                }
            }

            private String getName(Video video, Description description) {
                String name = description.getMethodName();
                String videoName = video.name();
                return videoName.length() < 3 ? name : videoName;
            }
        };
    }
}
