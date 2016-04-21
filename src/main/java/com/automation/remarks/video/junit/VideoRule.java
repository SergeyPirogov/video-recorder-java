package com.automation.remarks.video.junit;

import com.automation.remarks.video.recorder.VideoRecorder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.util.LinkedList;

import static com.automation.remarks.video.RecordingsUtil.deleteRecordingOnSuccess;

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
                VideoRecorder recorder = new VideoRecorder(description.getMethodName());
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
        };
    }
}
