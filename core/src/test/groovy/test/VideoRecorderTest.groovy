package test

import com.automation.remarks.video.enums.RecorderType
import com.automation.remarks.video.exception.RecordingException
import com.automation.remarks.video.recorder.monte.MonteRecorder
import spock.lang.Unroll

import static com.automation.remarks.video.recorder.VideoRecorder.conf

/**
 * Created by sergey on 09.10.16.
 */
@Unroll
class VideoRecorderTest extends SpockBaseTest {

    def "should be video file in folder with #type"() {
        given:
        System.getProperty("recorder.type", type.toString())

        when:
        File video = recordVideo()
        then:
        video.exists()

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be video in #name folder with #type"() {
        given:
        System.setProperty("recorder.type", type.toString())
        System.setProperty("video.folder", name)

        when:
        File video = recordVideo()
        then:
        video.parentFile.name == name

        where:
        name = "video"
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be absolute recording path with #type"() {
        given:
        System.setProperty("recorder.type", type.toString())

        when:
        File video = recordVideo()
        then:
        video.absolutePath.startsWith(new File(conf().folder()).getAbsolutePath() + File.separator + VIDEO_FILE_NAME)

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be exact video file name for #type"() {
        given:
        System.setProperty("recorder.type", type.toString())

        when:
        File video = recordVideo()
        then:
        video.exists()
        video.getName().startsWith(VIDEO_FILE_NAME)

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be recording exception for Monte if recording was not started"() {
        when:
        new MonteRecorder().stopAndSave(VIDEO_FILE_NAME)

        then:
        RecordingException ex = thrown()
        // Alternative syntax: def ex = thrown(InvalidDeviceException)
        ex.message == "Video recording wasn't started"
    }

    def "should record video with custom pixel format for #type"() {
        given:
        System.setProperty("recorder.type", type.toString())
        System.setProperty("ffmpeg.pixelFormat", "yuv444p")

        when:
        File video = recordVideo()
        then:
        video.exists()

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }
}
