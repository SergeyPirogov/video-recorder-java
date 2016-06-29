package com.automation.remarks.remote.tests

import com.automation.remarks.video.recorder.VideoRecorder
import spock.lang.Stepwise

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest
/**
 * Created by sergey on 5/14/16.
 */
@Stepwise
class NodeServletTest extends BaseTest {

    def setup() {
        VideoRecorder.conf().videoFolder.deleteDir()
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
        message.startsWith "recording stopped /home/sergey/VideoRecorder/remote/video/video_recording"
    }

    def "shouldBeDefaultFileName"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message.startsWith "recording stopped /home/sergey/VideoRecorder/remote/video/video_recording"
    }

    def "shouldBeFileNameAsNameRequestParameter"() {
        def name = "video"
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start?name=$name")
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message.startsWith "recording stopped /home/sergey/VideoRecorder/remote/video/video_recording"
        getVideoFiles().first().name =~ name
    }

    def "shouldNotCreateVideoFileIfSuccessTestKeyPassed"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        sendRecordingRequest(NODE_SERVLET_URL + "/stop?result=true")
        then:
        getVideoFiles().size() == 0
    }

    def "shouldCreateVideoFileIfFailTestKeyPassed"() {
        given:
        sendRecordingRequest(NODE_SERVLET_URL + "/start")
        when:
        sendRecordingRequest(NODE_SERVLET_URL + "/stop?result=false")
        then:
        getVideoFiles().size() == 1
    }

    @spock.lang.Ignore
    def "shouldBeWrongActionMessageOnStopIfWasNotStarted"() {
        when:
        def message = sendRecordingRequest(NODE_SERVLET_URL + "/stop")
        then:
        message == "Wrong Action! First, start recording"
    }
}

