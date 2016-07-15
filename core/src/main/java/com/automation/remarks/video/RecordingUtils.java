package com.automation.remarks.video;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private RecordingUtils() {
    }

    public static String doVideoProcessing(boolean successfulTest, File video) {
        if (video == null) {
            return "Video file is NULL";
        }
        if (!successfulTest) {
            String absolutePath = video.getAbsolutePath();
            System.err.println("Video recording\n" + absolutePath);
            return absolutePath;
        } else  {
            video.delete();
        }
        return "no recordings on success test";
    }
}
