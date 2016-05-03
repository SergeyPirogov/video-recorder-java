package com.automation.remarks.video;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingUtils {

    private RecordingUtils() {
    }

    public static void doVideoProcessing(boolean successfulTest, LinkedList<File> recordings) {
        if (!successfulTest) {
            System.err.println("Video recording\n" + recordings);
        } else if (recordings.size() > 0) {
            recordings.getFirst().delete();
        }
    }
}
