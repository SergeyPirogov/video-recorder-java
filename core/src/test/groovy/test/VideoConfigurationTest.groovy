package test

import com.automation.remarks.video.enums.RecorderType
import com.automation.remarks.video.recorder.VideoRecorder
import spock.lang.Unroll

/**
 * Created by sergey on 09.10.16.
 */
@Unroll
class VideoConfigurationTest extends SpockBaseTest {

    def "should be video file with default video folder path for #type"() {
        given:
        final String path = "${System.getProperty("user.dir")}${File.separator}video";

        VideoRecorder.conf()
                .videoEnabled(true)
                .withVideoFolder(path)
                .withRecorderType(type)

        when:
        File video = recordVideo()

        then:
        video.canonicalPath.startsWith(path)

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

    def "should be video screen size for #type"() {
        given:
        VideoRecorder.conf()
                .videoEnabled(true)
                .withScreenSize(640, 480)
                .withRecorderType(type);
        when:
        File video = recordVideo()

        then:
        video.exists()

        where:
        type << [RecorderType.FFMPEG, RecorderType.MONTE]
    }

}
