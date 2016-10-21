package com.automation.remarks.spock.test

import com.automation.remarks.video.annotations.Video
import com.automation.remarks.video.recorder.VideoRecorder
import spock.lang.Specification

/**
 * Created by sepi on 21.10.16.
 */
class SpockSpec extends Specification {

    def setupSpec(){

    }

    @Video
    def "demo test"() {
        expect:
        sleep(5000)
        2 != 2
    }
}
