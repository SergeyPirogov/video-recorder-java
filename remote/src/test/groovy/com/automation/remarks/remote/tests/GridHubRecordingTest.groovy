package com.automation.remarks.remote.tests

import com.automation.remarks.video.recorder.monte.MonteRecorder

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest
/**
 * Created by sergey on 5/14/16.
 */
class GridHubRecordingTest extends BaseTest {

    def setup() {
        MonteRecorder.conf().videoFolder.deleteDir()
    }

    def "shouldCreateVideoFile"() {
        final String name = "video_name"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start");
        when:
        sendRecordingRequest(HUB_SERVLET_URL + "/stop?name=${name}");
        def file = getVideoFiles().first()
        then:
        assert file.name.startsWith(name)
    }

    def "shouldCreateVideoFileIfFalseFlag"() {
        final String name = "video_name"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start?");
        when:
        sendRecordingRequest(HUB_SERVLET_URL + "/stop?result=false&name=${name}");
        def file = getVideoFiles().first()
        then:
        assert file.name.startsWith(name)
    }

    def "shouldNotCreateVideoFile"(){
        final String name = "video_name"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start?name=${name}");
        when:
        sendRecordingRequest(HUB_SERVLET_URL + "/stop?result=true");
        def listFiles = getVideoFiles()
        then:
        assert listFiles.size() == 0
    }
}
