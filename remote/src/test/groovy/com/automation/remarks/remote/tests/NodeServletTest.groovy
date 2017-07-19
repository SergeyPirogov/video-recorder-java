package com.automation.remarks.remote.tests

import com.automation.remarks.video.recorder.monte.MonteRecorder
import spock.lang.IgnoreIf
import spock.lang.Shared
import spock.lang.Stepwise

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest

/**
 * Created by sergey on 5/14/16.
 */
@Stepwise
class NodeServletTest extends BaseTest {

    @Shared
    String VIDEO_FOLDER = System.getProperty('user.dir')

    def setup() {
        new File(MonteRecorder.conf().folder()).deleteDir()
    }

    private static boolean isOsWindows() {
        System.properties['os.name'] == 'windows'
    }

    def "shouldBeOkMessageOnStartWithoutParameters"() {
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/start")
        then:
        message == "recording started"
    }

    def "shouldBeOkMessageOnStartWitParameters"() {
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/start?name=video")
        then:
        message == "recording started"
    }

    def "shouldBeFileNameInMessageOnStop"() {
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message.startsWith "recording stopped ${VIDEO_FOLDER}${File.separator}video${File.separator}video_recording"
    }

    def "shouldBeDefaultFileName"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message.startsWith "recording stopped ${VIDEO_FOLDER}${File.separator}video${File.separator}video_recording"
    }

    @IgnoreIf({ os.windows })
    def "shouldBeFileNameAsNameRequestParameter"() {
        def name = "video"
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start?name=$name")
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message.startsWith "recording stopped ${VIDEO_FOLDER}${File.separator}video${File.separator}video_recording"
        getVideoFiles().first().name =~ name
    }

    @IgnoreIf({ os.windows })
    def "shouldNotCreateVideoFileIfSuccessTestKeyPassed"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        sendRecordingRequest(NODE_SERVLET_URL + "/stop?result=true")
        then:
        getVideoFiles().size() == 0
    }

    @IgnoreIf({ os.windows })
    def "shouldCreateVideoFileIfFailTestKeyPassed"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        sendRecordingRequest(NODE_SERVLET_URL + "/stop?result=false")
        then:
        getVideoFiles().size() == 1
    }


    def "shouldBeCustomFolderForVideo"() {
        def folderName = "video"
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start?name=video&folder=${folderName}")
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop?result=false")
        then:
        message.startsWith "recording stopped ${VIDEO_FOLDER}${File.separator}${folderName}${File.separator}video_recording"
    }
}

