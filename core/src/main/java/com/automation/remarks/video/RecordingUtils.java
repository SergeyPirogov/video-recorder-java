package com.automation.remarks.video;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private RecordingUtils() {
    }

    public static String doVideoProcessing(boolean successfulTest, LinkedList<File> recordings) {
        if (recordings.size() == 0) {
            return "";
        }
        if (!successfulTest) {
            System.err.println("Video recording\n" + recordings);
            return recordings.getFirst().getAbsolutePath();
        } else if (recordings.size() > 0) {
            recordings.getFirst().delete();
        }
        return "no recordings on success test";
    }
}
