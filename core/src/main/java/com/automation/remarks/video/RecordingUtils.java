package com.automation.remarks.video;

import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private RecordingUtils() {
    }

    public static File doVideoProcessing(boolean successfulTest, LinkedList<File> recordings) {
        File video = null;
        if (!successfulTest) {
            System.err.println("Video recording\n" + recordings);
            video = recordings.getFirst();
            attachment(video);
        } else if (recordings.size() > 0) {
            recordings.getFirst().delete();
        }
        return video;
    }

    @Attachment(value = "video", type = "video/avi")
    public static byte[] attachment(File video) {
        try {
            return Files.readAllBytes(video.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
