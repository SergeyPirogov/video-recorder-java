package test

import com.automation.remarks.video.RecorderFactory
import com.automation.remarks.video.recorder.IVideoRecorder
import com.automation.remarks.video.recorder.VideoRecorder
import spock.lang.Specification

/**
 * Created by sergey on 09.10.16.
 */
public class SpockBaseTest extends Specification {

    public static final String VIDEO_FILE_NAME = "video_test";

    protected static File recordVideo() {
        IVideoRecorder recorder = RecorderFactory.getRecorder(VideoRecorder.conf().getRecorderType());
        recorder.start();
        sleep(5);
        return recorder.stopAndSave(VIDEO_FILE_NAME);
    }

    private static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

