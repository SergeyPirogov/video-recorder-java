package com.automation.remarks.remote.tests

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest
import static com.automation.remarks.video.VideoConfiguration.VIDEO_FOLDER
/**
 * Created by sergey on 5/14/16.
 */
class GridHubRecordingTest extends BaseTest {

    def setup() {
        new File(VIDEO_FOLDER).deleteDir()
    }

    def "shouldCreateVideoFile"() {
        final String name = "video_name"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start?name=${name}");
        when:
        sendRecordingRequest(HUB_SERVLET_URL + "/stop");
        def file = getVideoFiles().first()
        then:
        assert file.name.startsWith(name)
    }

    def "shouldCreateVideoFileIfFalseFlag"() {
        final String name = "video_name"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start?name=${name}");
        when:
        sendRecordingRequest(HUB_SERVLET_URL + "/stop?result=false");
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
