package com.automation.remarks.video;

import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.recorder.*;
import com.automation.remarks.video.recorder.ffmpeg.FFMpegRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.automation.remarks.video.recorder.ffmpeg.LinuxFFmpegRecorder;
import com.automation.remarks.video.recorder.ffmpeg.MacFFmpegRecorder;
import com.automation.remarks.video.recorder.ffmpeg.WindowsFFmpegRecorder;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.SystemUtils;

/**
 * Created by sepi on 19.07.16.
 */
public class RecorderFactory {

    public static IVideoRecorder getRecorder() {
        if (VideoRecorder.conf().getRecorderType().equals(RecorderType.FFMPEG)) {
            if (SystemUtils.IS_OS_WINDOWS) {
                return new WindowsFFmpegRecorder();
            } else if (SystemUtils.IS_OS_MAC) {
                return new MacFFmpegRecorder();
            }
            return new LinuxFFmpegRecorder();
        }
        return new MonteRecorder();
    }
}
