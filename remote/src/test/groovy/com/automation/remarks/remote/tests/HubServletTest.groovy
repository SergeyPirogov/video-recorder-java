package com.automation.remarks.remote.tests

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest

/**
 * Created by sergey on 5/14/16.
 */
class HubServletTest extends BaseTest{

    def "shouldBeMessageOnStartRecording"(){
        when:
        def message = sendRecordingRequest(HUB_SERVLET_URL + "/start");
        then:
        message == "http://localhost:5555 video command /start recording started"
    }

    def "shouldBeMessageOnStartRecordingWithNameParameter"(){
        when:
        def message = sendRecordingRequest(HUB_SERVLET_URL + "/start?name=video_name");
        then:
        message == "http://localhost:5555 video command /start recording started"
    }

    def "shouldBeFailMessageOnStartIfWrongRequestParameter"(){
        when:
        def message = sendRecordingRequest(HUB_SERVLET_URL + "/start/name");
        then:
        message.trim() == "http://localhost:5555 video command /start/name"
    }

    def "shouldBeCustomFolderForVideo"(){
        def folder = "custom_folder"
        given:
        sendRecordingRequest(HUB_SERVLET_URL + "/start?name=video_name&folder=${folder}");
        when:
        def message = sendRecordingRequest(HUB_SERVLET_URL + "/stop?result=false");
        then:
        message.startsWith "http://localhost:5555 video command /stop recording stopped ${System.getProperty("user.dir")}/custom_folder/video_name_recording"
    }
}
