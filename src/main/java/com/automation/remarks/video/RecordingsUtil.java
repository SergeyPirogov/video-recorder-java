package com.automation.remarks.video;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by sergey on 4/21/16.
 */
public class RecordingsUtil {

    private RecordingsUtil() {
    }

    public static void deleteRecordingOnSuccess(LinkedList<File> recordings) {
        if (recordings.size() > 0) {
            recordings.getFirst().delete();
        } else {
            System.err.println(recordings);
        }
    }
}
