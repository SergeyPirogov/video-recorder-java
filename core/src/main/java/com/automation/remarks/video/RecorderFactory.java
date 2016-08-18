package com.automation.remarks.video;

import com.automation.remarks.video.enums.RecorderType;
import com.automation.remarks.video.recorder.*;

/**
 * Created by sepi on 19.07.16.
 */
public class RecorderFactory {

       public static IVideoRecorder getRecorder(){
           if(VideoRecorder.conf().getRecorderType().equals(RecorderType.FFMPEG)){
               return new FFMpegRecorder();
           }
           return new MonteRecorder();
       }
}
