package com.automation.remarks.remote.test;

import com.automation.remarks.remote.StartGrid;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.automation.remarks.remote.utils.RestUtils.sendRecordingRequest;
import static java.lang.Thread.sleep;

/**
 * Created by Serhii_Pirohov on 11.05.2016.
 */
public class GridTest {

    private final String HUB_URL = "http://127.0.0.1:4444/grid/admin";

    @BeforeClass
    public static void startGrid() throws Exception {
        String[] args = {};
        StartGrid.main(args);
        sleep(1000);
    }

    @Test
    public void test1() throws Exception {
        sendRecordingRequest(HUB_URL + "/Video/start");
        sleep(1000);
        sendRecordingRequest(HUB_URL + "/Video/stop");
    }

    @Test
    public void test2() throws Exception {
        sendRecordingRequest(HUB_URL + "/Video/start");
        sleep(1000);
        sendRecordingRequest(HUB_URL + "/Video/stop?result=true");
    }
}
