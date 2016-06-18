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

    public static String doVideoProcessing(boolean successfulTest, LinkedList<File> recordings) {
        if(recordings.size() == 0){
            return "";
        }
        if (!successfulTest) {
            System.err.println("Video recording\n" + recordings);
            File video = recordings.getFirst();
            attachment(video);
            return video.getAbsolutePath();
        } else if (recordings.size() > 0) {
            recordings.getFirst().delete();
        }
        return "no recordings on success test";
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
