package com.automation.remarks.remote.tests

import com.automation.remarks.remote.StartGrid
import com.automation.remarks.video.recorder.VideoRecorder
import spock.lang.Specification

import static java.lang.Thread.sleep

/**
 * Created by sergey on 5/14/16.
 */
class BaseTest extends Specification {

    final String HUB_SERVLET_URL = "http://localhost:4444/grid/admin/Video";
    final String NODE_SERVLET_URL = "http://localhost:5555/extra/VideoServlet"

    def setupSpec() {
        runGrid()
    }

    def runGrid(){
        String[] args = [];
        StartGrid.main(args);
        sleep(1000);
    }

    def getVideoFiles(){
        VideoRecorder.conf().videoFolder.listFiles()
    }

}
