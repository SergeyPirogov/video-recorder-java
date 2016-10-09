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
        conf().withRecorderType(type)

        when:
        File video = recordVideo()
        then:
        video.exists()

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be video in #name folder with #type"() {
        given:
        conf().withVideoFolder(name).withRecorderType(type)

        when:
        File video = recordVideo()
        then:
        video.parentFile.name == name

        where:
        name = "videos"
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be absolute recording path with #type"() {
        given:
        conf().withRecorderType(type)

        when:
        File video = recordVideo();
        then:
        video.absolutePath.startsWith(conf().getVideoFolder().getAbsolutePath() + File.separator + VIDEO_FILE_NAME)

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be exact video file name for #type"() {
        given:
        conf().withRecorderType(type)

        when:
        File video = recordVideo();
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
}
